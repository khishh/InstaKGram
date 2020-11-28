package ca.khiraish.instagramclone.ui.account

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.source.UserRepository
import io.reactivex.rxjava3.core.CompletableObserver
import javax.inject.Inject

private const val TAG = "AccountViewModel"

class AccountViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    val username = ObservableField<String>()
    val fullname = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    fun isSignIn() {
        repository.isSignIn()
    }

    fun signUpClick(){
        if(username.get().isNullOrEmpty() || fullname.get().isNullOrEmpty() ||
            email.get().isNullOrEmpty() || password.get().isNullOrEmpty()){
            
        }
        else{
            Log.d(TAG, "signUpClick: Passed")
            repository.signUp(userName = username.get()!!, email = email.get()!!, password = password.get()!!, fullName = fullname.get()!!)
                .subscribe(
                    { Log.d(TAG, "signInClick: SUCCESS")},
                    { Log.d(TAG, "signInClick: FAIL")}
                )
                .dispose()
        }
    }

}