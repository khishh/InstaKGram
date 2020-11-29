package ca.khiraish.instagramclone.data.source.post

import ca.khiraish.instagramclone.data.model.Post
import io.reactivex.Completable

interface PostDataSource {
    fun savePost(post:Post): Completable
}