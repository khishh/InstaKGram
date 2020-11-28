package ca.khiraish.instagramclone.di.module

import androidx.lifecycle.ViewModelProvider
import ca.khiraish.instagramclone.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}