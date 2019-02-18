package com.tangpj.github.di

import com.tangpj.github.receiver.LoginTransferReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ReceiversModule{
    @ContributesAndroidInjector
    abstract fun contributesLoginTransferReceiver(): LoginTransferReceiver
}