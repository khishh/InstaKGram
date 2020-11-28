package ca.khiraish.instagramclone.di.component

import ca.khiraish.instagramclone.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (modules = [AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}