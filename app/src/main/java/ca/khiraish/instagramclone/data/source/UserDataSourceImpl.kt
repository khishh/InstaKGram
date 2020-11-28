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
                    if(task.isComplete){
                        task.result?.user?.uid?.let {
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it)
                                .setValue(User(userId = it, userName = userName, userEmail = email, userFullName = fullName, userBio = null, userImage = null))
                                .addOnSuccessListener { Log.d(TAG, "signUp: Success!!") }
                                .addOnFailureListener { Log.d(TAG, "signUp: Failed... ><")}
                        }
                    }
                    else{
                        emitter.onError(Throwable(task.exception?.message))
                    }
                }
        }
    }

    override fun signIn(email: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun signOut(): Completable {
        TODO("Not yet implemented")
    }

    override fun getUser(): Observable<User> {
        TODO("Not yet implemented")
    }
}