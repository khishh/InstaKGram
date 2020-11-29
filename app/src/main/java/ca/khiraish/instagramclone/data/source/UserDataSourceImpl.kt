package ca.khiraish.instagramclone.data.source

import android.util.Log
import ca.khiraish.instagramclone.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Observable

private const val TAG = "UserDataSourceImpl"
class UserDataSourceImpl : UserDataSource {

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
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it)
                                .setValue(User(userId = it, userName = userName, userEmail = email, userFullName = fullName, userBio = "", userImage = ""))
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
        TODO("Not yet implemented")
    }
}