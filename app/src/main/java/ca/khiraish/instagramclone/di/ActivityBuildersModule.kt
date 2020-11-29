package ca.khiraish.instagramclone.di

import ca.khiraish.instagramclone.di.account.AccountViewModelModule
import ca.khiraish.instagramclone.di.account.AccountFragmentModule
import ca.khiraish.instagramclone.di.main.MainFragmentModule
import ca.khiraish.instagramclone.di.main.MainViewModelModule
import ca.khiraish.instagramclone.ui.AccountActivity
import ca.khiraish.instagramclone.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [AccountViewModelModule::class, AccountFragmentModule::class]
    )
    fun contributeAccountActivity(): AccountActivity

    @ContributesAndroidInjector(
        modules = [MainViewModelModule::class, MainFragmentModule::class]
    )
    fun contributeMainActivity(): MainActivity
}