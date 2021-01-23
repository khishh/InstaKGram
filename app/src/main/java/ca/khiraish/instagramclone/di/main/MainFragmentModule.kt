package ca.khiraish.instagramclone.di.main

import ca.khiraish.instagramclone.di.FragmentScope
import ca.khiraish.instagramclone.ui.comment.CommentFragment
import ca.khiraish.instagramclone.ui.notification.NotificationFragment
import ca.khiraish.instagramclone.ui.post.PostFragment
import ca.khiraish.instagramclone.ui.profile.ProfileFragment
import ca.khiraish.instagramclone.ui.search.SearchFragment
import ca.khiraish.instagramclone.ui.timeline.TimelineFragment
import ca.khiraish.instagramclone.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun postFragment(): PostFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun timelineFragment(): TimelineFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun notificationFragment(): NotificationFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun userFragment(): UserFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun commentFragment(): CommentFragment
}