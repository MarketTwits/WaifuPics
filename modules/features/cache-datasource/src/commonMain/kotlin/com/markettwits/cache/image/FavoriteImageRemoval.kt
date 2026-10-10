package com.markettwits.cache.image

/** Original records and their neighbours, including the original saved timestamp. */
data class FavoriteImageRemoval(val before: List<ImageFavoriteCache>, val removed: List<ImageFavoriteCache>)

/** Restore around surviving neighbours without replacing newer saves or resurrecting other deletions. */
internal fun restoreFavoriteImages(current: List<ImageFavoriteCache>, removals: List<FavoriteImageRemoval>): List<ImageFavoriteCache> {
    var result = current
    for (removal in removals.asReversed()) {
        val restoredIds = removal.removed.map { it.id }.toSet()
        val indexes = result.mapIndexed { index, image -> image.id to index }.toMap()
        val existingIds = indexes.keys.toMutableSet()
        val existingUrls = result.map { it.netUrl }.toMutableSet()
        val nextSurvivor = IntArray(removal.before.size)
        var next = -1
        for (index in removal.before.indices.reversed()) {
            nextSurvivor[index] = next
            indexes[removal.before[index].id]?.let { next = it }
        }
        var previous = -1
        val insertions = mutableMapOf<Int, MutableList<ImageFavoriteCache>>()
        removal.before.forEachIndexed { index, image ->
            indexes[image.id]?.let { previous = it }
            if (image.id in restoredIds && image.id !in existingIds && image.netUrl !in existingUrls) {
                val anchor = nextSurvivor[index].takeIf { it >= 0 } ?: (previous + 1)
                insertions.getOrPut(anchor) { mutableListOf() }.add(image)
                existingIds.add(image.id)
                existingUrls.add(image.netUrl)
            }
        }
        result = buildList {
            for (index in 0..result.size) {
                insertions[index]?.let { addAll(it) }
                result.getOrNull(index)?.let { add(it) }
            }
        }
    }
    return result
}
