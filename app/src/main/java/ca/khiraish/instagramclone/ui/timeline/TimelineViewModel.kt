package ca.khiraish.instagramclone.ui.timeline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "TimelineViewModel"
class TimelineViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val followingUsers = MutableLiveData<List<User>>()
    val followingUserPosts = MutableLiveData<List<Post>>()

    fun getAllFollowers(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId.isNullOrEmpty()){
            Log.d(TAG, "===== getAllFollowers: userId is Null or empty")
        }else {
            userRepository.getAllFollowings(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        followingUsers.postValue(it)
                    },
                    onError = { throwable ->
                        Throwable(throwable)
                        Log.d(TAG, "===== getAllFollowers: onError $throwable")
                    }
                )
        }
    }

    fun getAllFollowingsPost(){
        val followingPosts = ArrayList<Post>()
        val followings = followingUsers.value!!
        var count = 0
        for (following: User in followings){
            Log.d(TAG, "getAllFollowingsPost: ${following.userId}")
            postRepository.fetchMyPost(following.userId!!)
                .observeOn(Schedulers.io())
                .map { posts: List<Post> ->  
                    for(post in posts){
                        post.userImage = following.userId
                        post.userImage = following.userImage
                    }
                    Log.d(TAG, "getAllFollowingsPost: post $posts")
                    posts
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        Log.d(TAG, "===== getAllFollowingsPost: onComplete with ${following.userId}")
                        Log.d(TAG, "getAllFollowingsPost: processing item on thread " + Thread.currentThread().name)
                        followingPosts.addAll(it)
                        count++;
                        if(count == followings.size){
                            Log.d(TAG, "===== getAllFollowingsPost: ALL onComplete with $followingPosts")
                            followingPosts.sortBy {post-> post.timestamp}
                            followingUserPosts.postValue(followingPosts)
                        }
                    },
                    onError = {Throwable("===== getAllFollowingsPost: onError $it")}
                )
        }

    }

}