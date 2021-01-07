package ca.khiraish.instagramclone.ui.user

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private const val TAG = "UserViewModel"
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {

//    lateinit var userId: String
    val userPosts = MutableLiveData<List<Post>>()
    val userPhotoUri = MutableLiveData<String>()
    val userName = ObservableField<String>()
    val numOfPosts = ObservableField<String>()
    val numOfFollowers = ObservableField<String>()
    val numOfFollowings = ObservableField<String>()
    val userBio = ObservableField<String>()
    
    fun fetchUserInfo(userId: String){
        userRepository.getUser(userId)
            .subscribeBy(
                onNext = { user ->
                    userName.set(user.userName)
                    userBio.set(user.userBio)
                    //ToDO let textfields have user info
                    userPhotoUri.postValue(user.userImage)
                },
                onError = {
                    Log.d(TAG, "fetchUserInfo: Error $it")
                }
            )
    }
    
    fun fetchUserPosts(userId: String){
        postRepository.fetchMyPost(userId)
            .subscribeBy(
                onNext = { posts ->
                    userPosts.postValue(posts)
                    numOfPosts.set(posts.size.toString())
                },
                onError = {
                    Log.d(TAG, "fetchUserPosts: Error $it")
                }
            )
    }

}