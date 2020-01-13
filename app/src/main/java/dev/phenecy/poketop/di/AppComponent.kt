package dev.phenecy.poketop.di

import dagger.Component
import dev.phenecy.poketop.MainActivity
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, RestModule::class, MvpModule::class, ProfileModule::class]
)

@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}