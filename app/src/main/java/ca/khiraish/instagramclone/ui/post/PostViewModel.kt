package ca.khiraish.instagramclone.ui.post

import android.net.Uri
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.Post
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostDataSource
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import ca.khiraish.instagramclone.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private const val TAG = "PostViewModel"

class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): ViewModel() {

    val caption = ObservableField<String>()
    val errorMsg = ObservableField<String>()
    lateinit var imageUri: Uri

    private val disposable = CompositeDisposable()

    val postSuccess = SingleLiveEvent<Void>()

    fun savePostClick() {
        // TODO :: create session manager class to hold currently login user object
        Log.d(TAG, "savePostClick: started")
        val description = caption.get()
        userRepository.getUser()
            .subscribeBy(
                onNext = {t ->
                    postRepository.savePost(Post(imageUri = imageUri.toString(), caption = description, userId = t.userId!!, userName = t.userName!!, userImage = t.userImage!!))
                        .subscribeBy (
                            onComplete = {
                            postSuccess.call()
                            Log.d(TAG, "savePostClick: savePost Success!! ${t.userImage}")
                            },
                        onError = {
                            errorMsg.set(it.toString())
                        }).addTo(disposable)
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}