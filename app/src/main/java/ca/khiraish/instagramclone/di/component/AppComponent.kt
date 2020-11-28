package ca.khiraish.instagramclone.di.component

import ca.khiraish.instagramclone.App
import ca.khiraish.instagramclone.di.ActivityBuildersModule
import ca.khiraish.instagramclone.di.module.AppModule
import ca.khiraish.instagramclone.di.module.DataModule
import ca.khiraish.instagramclone.di.module.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        DataModule::class,
        ViewModelFactoryModule::class
    ])
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}