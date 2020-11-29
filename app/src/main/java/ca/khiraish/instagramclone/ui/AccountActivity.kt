package ca.khiraish.instagramclone.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
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

    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        Log.d(TAG, "onCreate: " + viewModel.hashCode())
        progressBar = findViewById(R.id.account_progressbar)

        viewModel.authenticationPassed.observe(this, observer = Observer {
            Log.d(TAG, "onCreate: onChanged")
            Navigation.findNavController(this, R.id.account_fragment_container)
                .navigate(R.id.mainActivity)
            finish()
        })

        viewModel.authenticating.observe(this, Observer {
            Log.d(TAG, "onCreate: authenticating changed to $it")
            if(it) progressBar.visibility = View.VISIBLE
            else   progressBar.visibility = View.GONE
        })

        viewModel.isSignIn()
    }
    
}