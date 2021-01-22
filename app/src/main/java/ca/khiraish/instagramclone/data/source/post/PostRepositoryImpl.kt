package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postDataSource: PostDataSource)
    : PostRepository {
    override fun savePost(post: Post): Completable {
        return postDataSource.savePost(post)
    }

    override fun fetchMyPost(userId: String): Observable<List<Post>> {
        return postDataSource.fetchMyPost(userId)
    }

    override fun updateIsFav(
        postUserId: String,
        postId: String,
        newFavUsers: MutableMap<String, User>
    ): Completable {
        return postDataSource.updateIsFav(postUserId, postId, newFavUsers)
    }
}