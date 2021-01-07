package ca.khiraish.instagramclone.ui.user

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    postRepository: PostRepository
): ViewModel() {

    val numOfPosts = ObservableField<String>()

}