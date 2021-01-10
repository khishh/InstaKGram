package ca.khiraish.instagramclone.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.HashMap

data class Post constructor(
    @ServerTimestamp
    var timestamp: Date? = null,
    var postId: String? = null,
    val imageUri: String? = null,
    var userImage: String? = null,
    val caption: String? = null,
    val userId: String? = null,
    val userName: String? = null){
//    fun toMap(): Map<String, Any> =
//        HashMap<String, Any>().apply {
//            postI
//        }
}