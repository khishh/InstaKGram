package ca.khiraish.instagramclone.data.source.user

import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Observable

private const val TAG = "UserDataSourceImpl"
class UserDataSourceImpl :
    UserDataSource {

    private val fa = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val st = FirebaseStorage.getInstance()


    override fun isSignIn(): Observable<Boolean> =
        Observable.just(FirebaseAuth.getInstance().currentUser != null)

    override fun signUp(
        userName: String,
        email: String,
        password: String,
        fullName: String
    ): Completable {
        return Completable.create{ emitter ->
            fa.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        task.result?.user?.uid?.let {
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                .child(it)
//                                .setValue(User(userId = it, userName = userName, userEmail = email, userFullName = fullName, userBio = "", userImage = ""))
//                                .addOnSuccessListener { emitter.onComplete() }
//                                .addOnFailureListener { emitter.onError(Throwable(task.exception?.message))}
                            db.collection("Users")
                                .document(it)
                                .set(User(userId = it, userName = userName, userEmail = email, userFullName = fullName, userBio = "", userImage = ""))
                                .addOnSuccessListener { emitter.onComplete() }
                                .addOnFailureListener { emitter.onError(Throwable(task.exception?.message))}
                        }
                    }
                    else{
                        emitter.onError(Throwable(task.exception?.message))
                    }
                }
        }
    }

    override fun signIn(email: String, password: String): Completable {
        return  Completable.create { emitter ->
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }
                    else{
                        emitter.onError(Throwable(task.exception?.message))
                    }
                }
        }
    }

    override fun signOut(): Completable {
        TODO("Not yet implemented")
    }


    override fun getUser(): Observable<User> {
        return Observable.create {emitter ->
            val fbUser = fa.currentUser
            if(fbUser == null){
                emitter.onError(Throwable("You are not signed in"))
            }else{
                db.collection("Users")
                    .document(fbUser.uid)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject(User::class.java)
                        if(user != null){
                            emitter.onNext(user)
                        }else{
                            emitter.onError(Throwable("Could not fetch User data"))
                        }
                    }
            }
        }
    }

    override fun getUsers(): Observable<List<User>> {
        return Observable.create {emitter ->
            val fbUser = fa.currentUser;
            if(fbUser == null){
                emitter.onError(Throwable("You are not signed in"))
            }else{
                db.collection("Users")
                    .get()
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val users = ArrayList<User>()
                            for (t in task.result!!) {
                                val user = t.toObject(User::class.java)
                                users.add(user)
                            }
                            emitter.onNext(users)
                        }else{
                            emitter.onError(Throwable("Error: getUsers " + task.exception))
                        }
                    }.addOnFailureListener {
                        emitter.onError(Throwable("Error: getUsers $it"))
                    }
            }
        }
    }

    override fun getAllFollowings(userId: String): Observable<List<User>> {
        return Observable.create { emitter ->
            db.collection("User")
                .document(userId)
                .collection("Followings")
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val followings = ArrayList<User>()
                        for(f in task.result!!){
                            val following = f.toObject(User::class.java)
                            followings.add(following)
                        }
                        emitter.onNext(followings)
                    }else{
                        emitter.onError(Throwable("Error: getFollowings ${task.exception}"))
                    }
                }.addOnFailureListener { emitter.onError(Throwable("Error: getFollowings $it")) }
        }
    }

    override fun updateFollowings(userId: String, following: User): Completable {
        return Completable.create{emitter ->
            val dr = db.collection("User")
                .document(userId)
                .collection("Followings")
                .document(following.userId!!)
            dr.get().addOnSuccessListener {
                    if(it.exists()) dr.delete()
                    else dr.set(following)
                }
                .addOnCompleteListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(Throwable("updateFollowings $it")) }
        }
    }


}