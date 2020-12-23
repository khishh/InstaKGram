package ca.khiraish.instagramclone.data.source.post

import android.net.Uri
import android.util.Log
import ca.khiraish.instagramclone.data.model.Post
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PostDataSourceImpl"
class PostDataSourceImpl : PostDataSource {

    private val db = FirebaseFirestore.getInstance()
    private val st = FirebaseStorage.getInstance()

    override fun savePost(post: Post): Completable {
        return Completable.create{ emitter ->
            val imageUri = Uri.parse(post.imageUri)
            val fileName = "${SimpleDateFormat("yyyyMMdd__HHmm", Locale.CANADA).format(Date())}_${imageUri.lastPathSegment}"
            val filePath = st.getReference("Posts/${post.userId}").child(fileName)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl.addOnCompleteListener {
//                        val postId = db.collection("Posts").push().key
//                        if (postId != null) {
//                            db.getReference("Posts")
//                                .child(postId)
//                                .setValue(post)
//                                .addOnCompleteListener { emitter.onComplete() }
//                                .addOnFailureListener { emitter.onError(it) }
//                        }
//                        else{
//                            emitter.onError(Throwable("PostId was null"))
//                        }
                        val postId = db.collection("Posts").document().id
                        db.collection("Posts")
                            .document(post.userId!!)
                            .collection(post.userId)
                            .document(postId)
                            .set(post)
                            .addOnSuccessListener {
                                Log.d(TAG, "savePost: Success!! before call onComplete")
                                emitter.onComplete()
                            }
                            .addOnFailureListener { emitter.onError(it) }
                    }
                }.addOnFailureListener { emitter.onError(it) }
        }
    }

}