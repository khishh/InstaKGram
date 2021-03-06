package ca.khiraish.instagramclone.data.source.user

import ca.khiraish.instagramclone.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
    fun isSignIn(): Observable<Boolean>
    fun signUp(userName: String, email: String, password: String, fullName: String): Completable
    fun signIn(email: String, password: String): Completable
    fun signOut(): Completable
    fun getUser(): Observable<User>
    fun getUser(userId: String): Observable<User>
    fun getUsers(): Observable<List<User>>
    fun getNumOfFollowers(userId: String): Observable<Int>
    fun getNumOfFollowings(userId: String): Observable<Int>
    fun getAllFollowings(userId: String): Observable<List<User>>
    fun getAllFollowers(userId: String): Observable<List<User>>
    fun updateFollowing(ownerId: String, user: User): Observable<Boolean>
    fun updateFollower(owner: User, userId: String): Completable
    fun isFollowing(ownerId: String, userId: String): Observable<Boolean>
}