package ca.khiraish.instagramclone.ui.account

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.source.user.UserRepository
import ca.khiraish.instagramclone.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

private const val TAG = "AccountViewModel"

class AccountViewModel @Inject constructor(
    private val repository: UserRepository) : ViewModel() {

    val username = ObservableField<String>()
    val fullName = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val errorMessage = ObservableField<String>()

    val authenticationPassed = SingleLiveEvent<Void>()
    val authenticating = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun isSignIn() {
        disposable.add(repository.isSignIn()
            .subscribe { if (it) authenticationPassed.call() })
    }

    fun signUpClick(){
        authenticating.value = true
        if(username.get().isNullOrEmpty() || fullName.get().isNullOrEmpty() ||
            email.get().isNullOrEmpty() || password.get().isNullOrEmpty()){
            errorMessage.set("SignUpError: Please fill in all fields above")
        }
        else{
            disposable.add(repository.signUp(userName = username.get()!!, email = email.get()!!, password = password.get()!!, fullName = fullName.get()!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    authenticating.value = false
                }
                .subscribe(
                    {
                        Log.d(TAG, "signUpClick: SUCCESS")
                        authenticationPassed.call()
                    },
                    {
                        Log.d(TAG, "signUpClick: FAIL")
                        errorMessage.set("SignUpError: " + it.message)
                    }
                ))
        }
    }

    fun signInClick(){
        authenticating.value = true
        if(email.get().isNullOrEmpty() || password.get().isNullOrEmpty()){
            Log.d(TAG, "signInClick: " + email.get() + " " + password.get())
            errorMessage.set("SignInError: Please fill in all fields above")
        }
        else{
            Log.d(TAG, "signInClick: pass here")
            disposable.add(repository.signIn(email = email.get()!!, password = password.get()!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    authenticating.value = false
                }
                .subscribe(
                    {
                        Log.d(TAG, "signInClick: SUCCESS")
                        authenticationPassed.call()
                    },
                    {
                        Log.d(TAG, "signInClick: FAIL")
                        errorMessage.set("SignInError: " +it.message)
                    }
                ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}