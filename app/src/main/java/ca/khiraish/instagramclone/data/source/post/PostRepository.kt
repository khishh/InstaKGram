package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import io.reactivex.Completable

interface PostRepository {
    fun savePost(post: Post): Completable
}