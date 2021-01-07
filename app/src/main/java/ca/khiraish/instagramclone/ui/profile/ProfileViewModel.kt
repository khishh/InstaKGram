package ca.khiraish.instagramclone.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private const val TAG = "ProfileViewModel"
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {

    val myPosts = MutableLiveData<List<Post>>()

    fun fetchMyPost(){
        userRepository.getUser()
            .subscribeBy(
                onNext = { user ->
                    Log.d(TAG, "fetchMyPost: userId == ${user.userId}" )
                    postRepository.fetchMyPost(user.userId!!)
                        .subscribeBy(
                            onNext = {
                                myPosts.postValue(it)
                                Log.d(TAG, "===== fetchMyPost: Success!!")
                            },
                            onError = {
                                Log.d(TAG, "===== fetchMyPost: Error $it")
                            }
                        )
                },
                onError = {
                    Log.d(TAG, "===== fetchMyPost: Error $it")
                }
            )
    }

}