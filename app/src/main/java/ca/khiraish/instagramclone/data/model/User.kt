package ca.khiraish.instagramclone.data.model

data class User constructor(
    var userId: String? = null,
    var userName: String? = null,
    var userImage: String? = null,
    var userBio: String? = null,
    var userFullName: String? = null,
    var userEmail: String? = null) {
//    fun toMap(): Map<String, Any> =
//        HashMap<String, Any>().apply {
//            userId?.let { put("userId", it) }
//            userName?.let { put("userName", it) }
//            userImage?.let { put("userImage", it) }
//            userBio?.let { put("userBio", it) }
//            userFullName?.let { put("userFullName", it) }
//            userEmail?.let { put("userEmail", it) }
//        }
}