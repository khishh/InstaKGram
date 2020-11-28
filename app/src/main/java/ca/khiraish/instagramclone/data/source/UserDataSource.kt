package ca.khiraish.instagramclone.data.source


import ca.khiraish.instagramclone.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserDataSource {
    fun isSignIn(): Observable<Boolean>
    fun signUp(userName: String, email: String, password: String): Completable
    fun signIn(email: String, password: String): Completable
    fun signOut(): Completable
    fun getUser(): Observable<User>
}