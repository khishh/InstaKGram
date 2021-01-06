package ca.khiraish.instagramclone.ui.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import ca.khiraish.instagramclone.di.ViewModelFactory
import ca.khiraish.instagramclone.ui.post.PostViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class TimelineViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val users = MutableLiveData<List<User>>()

    fun getAllUsers(){
        userRepository.getUsers()
            .subscribeBy(
                onNext = {
                    users.postValue(it)
                },
                onError = { throwable ->
                    Throwable(throwable)
                    println("Error: getAllUser")
                }
            )
    }

}