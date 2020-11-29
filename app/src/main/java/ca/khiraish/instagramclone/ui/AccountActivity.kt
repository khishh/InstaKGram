package ca.khiraish.instagramclone.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ca.khiraish.instagramclone.R
import ca.khiraish.instagramclone.di.ViewModelFactory
import ca.khiraish.instagramclone.ui.account.AccountViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val TAG = "AccountActivity"
class AccountActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        Log.d(TAG, "onCreate: " + viewModel.hashCode())

        viewModel.authenticationPassed.observe(this, observer = Observer {
            Log.d(TAG, "onCreate: onChanged")
            Navigation.findNavController(this, R.id.account_fragment_container)
                .navigate(R.id.mainActivity)
            finish()
        })

        viewModel.isSignIn()
    }


}