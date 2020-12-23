package ca.khiraish.instagramclone.data.model

import android.net.Uri

data class Post constructor(
    val imageUri: String? = null,
    val caption: String? = null,
    val userId: String? = null,
    val userName: String? = null
)