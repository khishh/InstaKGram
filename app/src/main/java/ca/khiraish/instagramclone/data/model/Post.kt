package ca.khiraish.instagramclone.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post constructor(
    @ServerTimestamp
    var timestamp: Date? = null,
    var postId: String? = null,
    val imageUri: String? = null,
    val caption: String? = null,
    val userId: String? = null,
    val userName: String? = null
)