package ca.khiraish.instagramclone.di.account

import ca.khiraish.instagramclone.di.FragmentScope
import ca.khiraish.instagramclone.ui.account.SignInFragment
import ca.khiraish.instagramclone.ui.account.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AccountFragmentModule{
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun signInFragment(): SignInFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun signUpFragment(): SignUpFragment
}