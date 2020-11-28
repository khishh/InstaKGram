package ca.khiraish.instagramclone.di

import ca.khiraish.instagramclone.di.account.AccountViewModelModule
import ca.khiraish.instagramclone.di.account.AccountFragmentModule
import ca.khiraish.instagramclone.ui.AccountActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [AccountViewModelModule::class, AccountFragmentModule::class]
    )
    fun contributeAccountActivity(): AccountActivity
}