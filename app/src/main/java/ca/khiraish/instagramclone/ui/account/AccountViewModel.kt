package ca.khiraish.instagramclone.ui.account

import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.data.source.UserRepository
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
}