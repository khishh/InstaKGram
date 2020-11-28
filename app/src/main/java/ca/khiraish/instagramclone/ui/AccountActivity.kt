package ca.khiraish.instagramclone.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
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

    }


}