package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import io.reactivex.Completable
import io.reactivex.Observable

interface PostDataSource {
    fun savePost(post:Post): Completable
    fun fetchMyPost(userId: String): Observable<List<Post>>
}