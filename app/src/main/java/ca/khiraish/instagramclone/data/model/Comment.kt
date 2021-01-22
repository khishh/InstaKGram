package ca.khiraish.instagramclone.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Comment constructor(
    @ServerTimestamp
    var timestamp: Date? = null,
    val commentId: String,
    val userId: String,
    var userName: String,
    var userImage: String? = null,
    var content: String,
    val replies: MutableList<Comment> = mutableListOf()
)