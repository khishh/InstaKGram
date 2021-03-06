package ca.khiraish.instagramclone.data.source.post

import android.net.Uri
import android.util.Log
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Observable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "PostDataSourceImpl"
class PostDataSourceImpl : PostDataSource {

    private val db = FirebaseFirestore.getInstance()
    private val st = FirebaseStorage.getInstance()

    override fun savePost(post: Post): Completable {
        return Completable.create{ emitter ->
            Log.d(TAG, "savePost: ${Thread.currentThread().name}")
            val imageUri = Uri.parse(post.imageUri)
            val fileName = "${SimpleDateFormat("yyyyMMdd__HHmm", Locale.CANADA).format(Date())}_${imageUri.lastPathSegment}"
            val filePath = st.getReference("Posts/${post.userId}").child(fileName)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    filePath.downloadUrl.addOnCompleteListener {
                        val postId = db.collection("Posts").document().id
                        val timestamp = FieldValue.serverTimestamp()
                        val content = hashMapOf<String, Any>(
                            "postId" to postId,
                            "imageUri" to it.result.toString(),
                            "caption" to post.caption!!,
                            "userId" to post.userId!!,
                            "userImage" to post.userImage!!,
                            "userName" to post.userName!!,
                            "timestamp" to timestamp,
                            "favUsers" to post.favUsers
                        )
                        Log.d(TAG, "===== savePost: post savetime == $timestamp")
                        db.collection("Posts")
                            .document(post.userId)
                            .collection(post.userId)
                            .document(postId)
                            .set(content)
                            .addOnSuccessListener {
                                Log.d(TAG, "savePost: Success!! before call onComplete")
                                emitter.onComplete()
                            }
                            .addOnFailureListener { emitter.onError(it) }
                    }
                }.addOnFailureListener { emitter.onError(it) }
        }
    }

    override fun fetchMyPost(userId: String): Observable<List<Post>> {
        return Observable.create { emitter ->
            Log.d(TAG, "fetchMyPost: processing item on thread " + Thread.currentThread().name)
            db.collection("Posts")
                .document(userId)
                .collection(userId)
                .orderBy("timestamp", Query.Direction.DESCENDING) // TODO order by timestamp by adding timestamp field to Post class
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val myPosts = ArrayList<Post>()
                        for(p in task.result!!){
                            val post = p.toObject(Post::class.java)
                            myPosts.add(post)
                        }
                        emitter.onNext(myPosts)
                    }else {
                        emitter.onError(Throwable("Error: fetchMyPost ${task.exception}"))
                    }
                }
                .addOnFailureListener { Throwable("Error: fetchMyPost $it") }
        }
    }

    override fun updateIsFav(
        postUserId: String,
        postId: String,
        newFavUsers: MutableMap<String, User>
    ): Completable {
        Log.d(TAG, "updateIsFav: $newFavUsers")
        return Completable.create{ emitter ->
            Log.d(TAG, "updateIsFav: ${Thread.currentThread().name}")
            db.collection("Posts")
                .document(postUserId)
                .collection(postUserId)
                .document(postId)
                .update("favUsers", newFavUsers)
                .addOnCompleteListener {
                    Log.d(TAG, "updateIsFav: SUCCESS!!")
                    emitter.onComplete()
                }
                .addOnFailureListener { emitter.onError(Throwable("updateIsFav: Error $it")) }
        }
    }


}