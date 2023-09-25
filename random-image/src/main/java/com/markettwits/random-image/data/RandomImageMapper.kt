package com.markettwits.waifupics.view.main.data

import com.markettwits.`random-image`.ui.AuthorUi
import com.markettwits.`random-image`.ui.ImageSourceUi
import com.markettwits.`random-image`.ui.RandomImageUiState
import com.markettwits.`random-image`.ui.UploaderUi
import com.markettwits.waifupics.view.main.data.net.models.AuthorCloud
import com.markettwits.waifupics.view.main.data.net.models.DataCloud
import com.markettwits.waifupics.view.main.data.net.models.UserCloudData

interface RandomImageUiMapper {
    fun map(cloud: DataCloud, user: UserCloudData, authorCloud: AuthorCloud?): RandomImageUiState
    fun mapWithoutOwner(cloud: DataCloud): RandomImageUiState
    fun mapWithOwner(cloud: DataCloud, user : UserCloudData) : RandomImageUiState
    fun mapWithAuthor(cloud: DataCloud, user: UserCloudData, authorCloud: AuthorCloud) : RandomImageUiState
    class Base : RandomImageUiMapper {
        override fun map(
            cloud: DataCloud,
            user: UserCloudData,
            authorCloud: AuthorCloud?
        ): RandomImageUiState {
            return if (authorCloud == null) {
                mapWithOwner(cloud, user)
            }else{
                mapWithAuthor(cloud, user, authorCloud)
            }
        }
        override fun mapWithoutOwner(cloud: DataCloud): RandomImageUiState {
            return RandomImageUiState.SuccessEmptyAuthor(
                imageUrl = cloud.attributes.file,
                imageData = ImageSourceUi(
                    source = cloud.attributes.source.name ?: "",
                    sourceUrl = cloud.attributes.source.url ?: "",
                    ageRating = cloud.attributes.ageRating
                ),
                collorPallente = cloud.attributes.colors.palette
            )
        }
        override fun mapWithOwner(cloud: DataCloud,user : UserCloudData): RandomImageUiState {
            return RandomImageUiState.SuccessWithOwner(
                imageUrl = cloud.attributes.file,
                imageData = ImageSourceUi(
                    source = cloud.attributes.source.name ?: "",
                    sourceUrl = cloud.attributes.source.url ?: "",
                    ageRating = cloud.attributes.ageRating
                ),
                collorPallente = cloud.attributes.colors.palette,
                owner = UploaderUi(user.attributes.username, user.attributes.avatarImage)
            )
        }

        override fun mapWithAuthor(
            cloud: DataCloud,
            user: UserCloudData,
            authorCloud: AuthorCloud
        ) : RandomImageUiState {
            return RandomImageUiState.SuccessWithAuthor(
                imageUrl = cloud.attributes.file,
                imageData = ImageSourceUi(
                    source = cloud.attributes.source.name ?: "",
                    sourceUrl = cloud.attributes.source.url ?: "",
                    ageRating = cloud.attributes.ageRating
                ),
                collorPallente = cloud.attributes.colors.palette,
                author = AuthorUi(authorCloud.data.attributes.imageUrl, authorCloud.data.attributes.name, authorCloud.data.attributes.aliases, authorCloud.data.attributes.officialLinks)
            )
        }
    }
}