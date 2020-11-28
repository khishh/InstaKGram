package ca.khiraish.instagramclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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
        ViewModelProviders.of(this,viewModelFactory).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        if(viewModel != null){
            Log.d(TAG, "onCreate: viewModel is created");
        }
        else{
            Log.d(TAG, "onCreate: viewModel is not created");
        }
    }


}