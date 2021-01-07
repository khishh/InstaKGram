package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
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
}