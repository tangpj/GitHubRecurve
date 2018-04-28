package tang.com.github

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import tang.com.github.di.DaggerAppComponent

class GitHubApp: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().application(this).build()
}