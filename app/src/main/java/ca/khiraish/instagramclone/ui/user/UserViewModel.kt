package ca.khiraish.instagramclone.ui.user

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

private const val TAG = "UserViewModel"
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
): ViewModel() {

//    lateinit var userId: String
//    private lateinit var user: User
    val user = MutableLiveData<User>()
    val userPosts = MutableLiveData<List<Post>>()
    val userPhotoUri = MutableLiveData<String>()
    val userName = ObservableField<String>()
    val numOfPosts = ObservableField<String>()
    val numOfFollowers = ObservableField<String>()
    val numOfFollowings = ObservableField<String>()
    val userBio = ObservableField<String>()
    val followStatus = ObservableField<String>()
    
    fun fetchUserInfo(userId: String){
        userRepository.getUser(userId)
            .subscribeBy(
                onNext = { _user ->
//                    this.user = user // TODO assigning user here may cause user to be null in getAlllFollowers()
                    user.postValue(_user)
                    userName.set(_user.userName)
                    userBio.set(_user.userBio)
                    //ToDO let textfields have user info
                    userPhotoUri.postValue(_user.userImage)
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

    fun getFollowingStatus(userId: String){
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid
        if(ownerId.isNullOrEmpty()){
            Log.d(TAG, "===== getFollowingStatus: Error ownerId not found")
        }else{
            userRepository.isFollowing(ownerId, userId)
                .subscribeBy(
                    onNext = {
                        if(it) {
                            followStatus.set("Following")
                            Log.d(TAG, "===== getFollowingStatus: is Following")
                        }
                        else{
                            followStatus.set("Follow")
                            Log.d(TAG, "===== getFollowingStatus: is Not Following")
                        }
                    }
                )
        }
    }

    fun updateFollowingStatus(userId: String){
        val ownerId = FirebaseAuth.getInstance().currentUser?.uid
        Log.d(TAG, "===== updateFollowingStatus: ownerId == $ownerId")
        if(ownerId.isNullOrEmpty()){
            Log.d(TAG, "===== updateFollowingStatus: Error ownerId not found")
        }else{
            userRepository.updateFollowing(ownerId, user.value!!)
                .subscribeBy(
                    onNext = {
                        if(it) {
                            followStatus.set("Following")
                            Log.d(TAG, "===== updateFollowingStatus: is Following")
                        }
                        else{
                            followStatus.set("Follow")
                            Log.d(TAG, "===== updateFollowingStatus: is Not Following")
                        }
                    },
                    onComplete = {
                        Log.d(TAG, "===== updateFollowingStatus: Success!!")
                    },
                    onError = {
                        Log.d(TAG, "===== updateFollowingStatus: Error!! $it")
                    }
                )
        }
    }

    fun updateFollowerStatus(userId: String){
        userRepository.getUser() // TODO fix by UserManager
            .subscribeBy(
                onNext = {owner->
                    userRepository.updateFollower(owner, userId)
                        .subscribeBy(
                            onComplete = {
                                Log.d(TAG, "===== updateFollowerStatus: Success!!")
                                this.getNumOfFollowers(userId)
                            },
                            onError = {
                                Log.d(TAG, "===== updateFollowerStatus: Error!! $it")
                            }
                        )
                }
            )
    }

    fun getNumOfFollowers(userId: String){
        userRepository.getNumOfFollowers(userId)
            .subscribeBy(
                onNext = {
                    numOfFollowers.set(it.toString())
                    Log.d(TAG, "===== getNumOfFollowers: $it")
                },
                onError = {
                    Log.d(TAG, "===== getAllFollowers: Error!! $it")
                }
            )
    }

    fun getNumOfFollowings(userId: String){
        userRepository.getNumOfFollowings(userId)
            .subscribeBy(
                onNext = {
                    numOfFollowings.set(it.toString())
                    Log.d(TAG, "getAllFollowings: $it")
                },
                onError = {
                    Log.d(TAG, "===== getAllFollowings: Error!! $it")
                }
            )
    }


    fun getOwnerUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

}