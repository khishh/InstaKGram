package ca.khiraish.instagramclone.data.model

import android.net.Uri

data class Post(
    val imageUri: Uri,
    val caption: String,
    val userId: String,
    val userName: String
)