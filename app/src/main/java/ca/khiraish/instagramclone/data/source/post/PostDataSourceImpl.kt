package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.text.SimpleDateFormat
import java.util.*

class PostDataSourceImpl : PostDataSource {

    private val db = FirebaseDatabase.getInstance()
    private val st = FirebaseStorage.getInstance()

    override fun savePost(post: Post): Completable {
        return Completable.create { emitter ->
            val fileName = "${SimpleDateFormat("yyyyMMdd__HHmm", Locale.CANADA).format(Date())}_${post.imageUri.lastPathSegment}"
            val filePath = st.getReference("Posts/${post.userId}").child(fileName)
            filePath.putFile(post.imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl.addOnCompleteListener {
                        val postId = db.getReference("Posts").push().key
                        if (postId != null) {
                            db.getReference("Posts")
                                .child(postId)
                                .setValue(post)
                                .addOnCompleteListener { emitter.onComplete() }
                                .addOnFailureListener { emitter.onError(it) }
                        }
                        else{
                            emitter.onError(Throwable("PostId was null"))
                        }
                    }
                }.addOnFailureListener { emitter.onError(it) }
        }
    }

}