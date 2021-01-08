package ca.khiraish.instagramclone.ui.profile

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private const val TAG = "ProfileViewModel"
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {

    val myPosts = MutableLiveData<List<Post>>()
    val userName= ObservableField<String>()
    val numOfPosts = ObservableField<String>()
    val numOfFollowers = ObservableField<String>()
    val numOfFollowings = ObservableField<String>()
    val myBio = ObservableField<String>()


    fun fetchMyPost(){
        userRepository.getUser()
            .subscribeBy(
                onNext = { user ->
                    Log.d(TAG, "fetchMyPost: userId == ${user.userId}" )
                    userName.set(user.userName)
                    postRepository.fetchMyPost(user.userId!!)
                        .subscribeBy(
                            onNext = {
                                myPosts.postValue(it)
                                numOfPosts.set(it.size.toString())
                                Log.d(TAG, "===== fetchMyPost: Success!! ${it.size}")
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

    fun getNumOfFollowers(){
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid
        Log.d(TAG, "===== getNumOfFollowers: ownerId == $ownerId")
        if(ownerId.isNullOrEmpty()) {
            Log.d(TAG, "===== getNumOfFollowers: Error ownerId not found")
        }else {
            userRepository.getNumOfFollowers(ownerId)
                .subscribeBy(
                    onNext = {
                        numOfFollowers.set(it.toString())
                        Log.d(TAG, "===== getNumOfFollowers: $it")
                    },
                    onError = {
                        Log.d(TAG, "===== getNumOfFollowers: Error!! $it")
                    }
                )
        }
    }

    fun getNumOfFollowings(){
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid
        Log.d(TAG, "===== getNumOfFollowings: ownerId == $ownerId")
        if(ownerId.isNullOrEmpty()) {
            Log.d(TAG, "===== getNumOfFollowings: Error ownerId not found")
        }else {
            userRepository.getNumOfFollowings(ownerId)
                .subscribeBy(
                    onNext = {
                        numOfFollowings.set(it.toString())
                        Log.d(TAG, "===== getNumOfFollowings: $it")
                    },
                    onError = {
                        Log.d(TAG, "===== getNumOfFollowings: Error!! $it")
                    }
                )
        }
    }

}