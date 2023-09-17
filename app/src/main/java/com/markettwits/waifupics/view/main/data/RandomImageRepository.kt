package com.markettwits.waifupics.view.main.data

import com.markettwits.waifupics.view.main.data.net.MakeService
import com.markettwits.waifupics.view.main.data.net.models.AuthorCloud
import com.markettwits.waifupics.view.main.data.net.models.UserCloud
import com.markettwits.waifupics.view.main.ui.RandomImageUiState

interface RandomImageRepository {
    suspend fun fetchRandomImage(filters : String) : RandomImageUiState
    suspend fun fetchUser(userId : String) : UserCloud
    suspend fun fetchAuthor(authorId : String) : AuthorCloud
    suspend fun preloadImage(url : String)
    class Base(
        private val imageLoader: ImageLoader,
        private val service: MakeService,
        private val mapper: RandomImageUiMapper
    ) : RandomImageRepository{
        override suspend fun fetchRandomImage(filters: String) : RandomImageUiState {
            return try {
                val request = service.service().randomImage(filters)
                val user = fetchUser(request.data.relationships.uploader.data!!.id)
                val authorData = request.data.relationships.artist.data
                val author = if (authorData != null) fetchAuthor(authorData.id) else null
                val image = mapper.map(request.data, user.data, author)
                image
            }catch (e : Exception){
                RandomImageUiState.Error(e.message.toString())
            }
        }

        override suspend fun fetchUser(userId: String) = service.service().fetchUserById(userId)
        override suspend fun fetchAuthor(authorId: String) = service.service().fetchArtistById(authorId)


        override suspend fun preloadImage(url: String) {
           imageLoader.load(url)
        }
    }
}