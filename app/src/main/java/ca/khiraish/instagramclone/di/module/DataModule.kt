package ca.khiraish.instagramclone.di.module

import ca.khiraish.instagramclone.data.source.post.PostDataSource
import ca.khiraish.instagramclone.data.source.post.PostDataSourceImpl
import ca.khiraish.instagramclone.data.source.post.PostRepository
import ca.khiraish.instagramclone.data.source.post.PostRepositoryImpl
import ca.khiraish.instagramclone.data.source.user.UserDataSource
import ca.khiraish.instagramclone.data.source.user.UserDataSourceImpl
import ca.khiraish.instagramclone.data.source.user.UserRepository
import ca.khiraish.instagramclone.data.source.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRepository(dataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(dataSource)

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserDataSource(): UserDataSource =
        UserDataSourceImpl()

    @Singleton
    @Provides
    @JvmStatic
    fun providePostRepository(postSource: PostDataSource): PostRepository =
        PostRepositoryImpl(postSource)

    @Singleton
    @Provides
    @JvmStatic
    fun providePostDataSource(): PostDataSource =
        PostDataSourceImpl()
}