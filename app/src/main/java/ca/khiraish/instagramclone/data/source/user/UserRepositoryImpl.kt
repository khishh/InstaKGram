package ca.khiraish.instagramclone.data.source.user

import ca.khiraish.instagramclone.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override fun isSignIn(): Observable<Boolean> {
        return userDataSource.isSignIn()
    }

    override fun signUp(userName: String, email: String, password: String, fullName: String): Completable {
        return userDataSource.signUp(userName, email, password, fullName)
    }

    override fun signIn(email: String, password: String): Completable {
        return userDataSource.signIn(email, password)
    }

    override fun signOut(): Completable {
        TODO("Not yet implemented")
    }

    override fun getUser(): Observable<User> {
        return userDataSource.getUser()
    }

    override fun getUser(userId: String): Observable<User> {
        return userDataSource.getUser(userId)
    }

    override fun getUsers(): Observable<List<User>> {
        return userDataSource.getUsers()
    }

    override fun getAllFollowings(userId: String): Observable<List<User>> {
        return userDataSource.getAllFollowings(userId)
    }

    override fun updateFollowing(userId: String, following: String): Observable<Boolean> {
        return userDataSource.updateFollowing(userId, following)
    }

    override fun isFollowing(ownerId: String, userId: String): Observable<Boolean> {
        return userDataSource.isFollowing(ownerId, userId)
    }


}