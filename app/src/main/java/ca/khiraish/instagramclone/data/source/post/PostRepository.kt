package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface PostRepository {
    fun savePost(post: Post): Completable
    fun fetchMyPost(userId: String): Observable<List<Post>>
    fun updateIsFav(postUserId: String, postId: String, newFavUsers: MutableMap<String, User>): Completable

}