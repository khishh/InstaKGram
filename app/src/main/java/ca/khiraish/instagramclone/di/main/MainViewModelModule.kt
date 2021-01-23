package ca.khiraish.instagramclone.di.main

import androidx.lifecycle.ViewModel
import ca.khiraish.instagramclone.di.ViewModelKey
import ca.khiraish.instagramclone.ui.comment.CommentViewModel
import ca.khiraish.instagramclone.ui.post.PostViewModel
import ca.khiraish.instagramclone.ui.profile.ProfileViewModel
import ca.khiraish.instagramclone.ui.search.SearchViewModel
import ca.khiraish.instagramclone.ui.timeline.TimelineViewModel
import ca.khiraish.instagramclone.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(viewModel: PostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimelineViewModel::class)
    abstract fun bindTimelineViewModel(viewModel: TimelineViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel::class)
    abstract fun bindCommentViewModel(viewModel: CommentViewModel): ViewModel
}