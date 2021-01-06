package ca.khiraish.instagramclone.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.model.User
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.user.UserRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val users = MutableLiveData<List<User>>()
//    val followings = MutableLiveData<List<User>>()

//    fun getAllFollowers(){
//        userRepository.getUser()
//            .subscribeBy(
//                onNext = { user ->
//                    userRepository.getAllFollowings(user.userId!!)
//                        .subscribeBy (
//                            onNext = {followings.postValue(it)},
//                            onError = {
//                                println("Error getAllFollowers $it")
//                                Throwable(it)
//                            }
//                        )
//                },
//                onError = {println("Error getAllFollowers $it")
//                        Throwable(it)}
//            )
//    }
//
//    fun updateFollowing(clickedUser: User){
//        userRepository.getUser()
//            .subscribeBy(
//                onNext = {
//                    userRepository.updateFollowings(it.userId!!, clickedUser)
//                        .subscribeBy (
//                            onComplete = { println("updateFollowing completed")},
//                            onError =  {println("Error: updateFollowing failed")}
//                        )
//                },
//                onError = {println("Error: updateFollowing failed")}
//            )
//    }

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