package ca.khiraish.instagramclone.di.account

import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.di.ViewModelKey
import ca.khiraish.instagramclone.ui.account.AccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AccountViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel

}