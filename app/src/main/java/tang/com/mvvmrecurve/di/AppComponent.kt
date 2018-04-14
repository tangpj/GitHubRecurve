package tang.com.mvvmrecurve.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import tang.com.mvvmrecurve.GitHubApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent: AndroidInjector<GitHubApp>{
    @Component.Builder
    interface Builder{

        @BindsInstance fun application(app: GitHubApp): AppComponent.Builder

        fun build(): AppComponent
    }
}