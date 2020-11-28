package ca.khiraish.instagramclone.di.module

import ca.khiraish.instagramclone.data.source.UserDataSource
import ca.khiraish.instagramclone.data.source.UserDataSourceImpl
import ca.khiraish.instagramclone.data.source.UserRepository
import ca.khiraish.instagramclone.data.source.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRepository(dataSource: UserDataSource): UserRepository = UserRepositoryImpl(dataSource)

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserDataSource(): UserDataSource = UserDataSourceImpl()
}