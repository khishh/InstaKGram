package ca.khiraish.instagramclone.data.source.user

import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable

private const val TAG = "UserDataSourceImpl"
class UserDataSourceImpl :
    UserDataSource {

    override fun isSignIn(): Observable<Boolean> =
        Observable.just(FirebaseAuth.getInstance().currentUser != null)

    override fun signUp(
        userName: String,
        email: String,
        password: String,
        fullName: String
    ): Completable {
        return Completable.create{ emitter ->
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        task.result?.user?.uid?.let {
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                .child(it)
//                                .setValue(User(userId = it, userName = userName, userEmail = email, userFullName = fullName, userBio = "", userImage = ""))
//                                .addOnSuccessListener { emitter.onComplete() }
//                                .addOnFailureListener { emitter.onError(Throwable(task.exception?.message))}
                            FirebaseFirestore.getInstance().collection("Users")
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
            val fbUser = FirebaseAuth.getInstance().currentUser
            if(fbUser == null){
                emitter.onError(Throwable("You are not signed in"))
            }else{
                FirebaseFirestore.getInstance().collection("Users")
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
}