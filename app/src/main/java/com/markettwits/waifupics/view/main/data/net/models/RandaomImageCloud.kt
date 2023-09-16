package com.markettwits.waifupics.view.main.data.net.models

data class RandomImageCloud(val data : DataCloud)

data class DataCloud(
    val type : String,
    val id : String,
    val attributes : AttributesCloud,
    val relationships :RelationshipsCloud
)
data class RelationshipsCloud(
    val uploader : PersonCloud,
    val artist : PersonCloud
)
data class PersonCloud(val links: Links, val data: Data?){
    data class Links(val links : String,  val related : String)
    data class Data(val type: String, val id : String)
}
data class AttributesCloud(
    val file : String,
    val title : String,
    val ageRating : String,
    val colors : ColorsCloud,
    val source : SourceCloud
)
data class SourceCloud(
    val name : String?,
    val url : String?
)
data class ColorsCloud(
    val dominant : String,
    val palette : List<String>
)
