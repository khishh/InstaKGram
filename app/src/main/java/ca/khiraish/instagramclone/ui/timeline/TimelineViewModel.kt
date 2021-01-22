package ca.khiraish.instagramclone.ui.timeline

import android.util.Log
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

private const val TAG = "TimelineViewModel"
class TimelineViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val behaviorSubject = BehaviorSubject.create<List<Post>>()
    private val disposable = CompositeDisposable()

    fun getAllFollowers(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId.isNullOrEmpty()){
            Log.d(TAG, "===== getAllFollowers: userId is Null or empty")
        }else {
            userRepository.getAllFollowings(userId)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = {
                        getAllFollowingsPost(it)
                    },
                    onError = { throwable ->
                        Throwable(throwable)
                        Log.d(TAG, "===== getAllFollowers: onError $throwable")
                    }
                ).addTo(disposable)
        }
    }

    private fun getAllFollowingsPost(followings : List<User>){
        val followingPosts = ArrayList<Post>()
        var count = 0

        followings.forEach { following ->
            Observables.combineLatest(
                postRepository.fetchMyPost(following.userId!!),
                userRepository.getUser())
            { posts, u ->
                posts.map { post ->  checkIsFav(post, u) }
            }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                        Log.d(TAG, "===== getAllFollowingsPost: onComplete with ${following.userId}")
                        Log.d(TAG, "getAllFollowingsPost: processing item on thread " + Thread.currentThread().name)
                        followingPosts.addAll(it)
                        count++;
                        if(count == followings.size){
                            Log.d(TAG, "===== getAllFollowingsPost: ALL onComplete with $followingPosts")
                            followingPosts.sortByDescending {post-> post.timestamp}
                            behaviorSubject.onNext(followingPosts)
                        }
                    },
                    onError = {Throwable("===== getAllFollowingsPost: onError $it")}
            ).addTo(disposable)
        }
    }



    private fun checkIsFav(post: Post, user: User) =
        post.apply {
            if(favUsers.containsKey(user.userId)) isFav = true
        }

    fun updateIsFav(
        postId : String,
        createNumLikesText: (Int) -> Unit
    ){
        val post = behaviorSubject.value?.first{it.postId == postId} ?: return
        userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMapCompletable {user ->
                if(post.isFav){
                    post.favUsers[user.userId!!] = user
                }else{
                    post.favUsers.remove(user.userId)
                }
                createNumLikesText(post.favUsers.size)
                postRepository.updateIsFav(post.userId!!, post.postId!!, post.favUsers)
            }
            .subscribeBy(
                onComplete = { Log.d(TAG, "updateIsFav: updateIsFav:: SUCCESS!!")},
                onError = { Log.d(TAG, "updateIsFav: updateIsFav:: Failure!!") }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}