package ca.khiraish.instagramclone.di.main

import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.di.ViewModelKey
import ca.khiraish.instagramclone.ui.post.PostViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(viewModel: PostViewModel): ViewModel
}