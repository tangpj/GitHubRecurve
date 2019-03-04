package com.tangpj.github.domain

import androidx.annotation.IntDef

@IntDef(value = [RepoFlag.NORMAL, RepoFlag.STAR, RepoFlag.FORKED, RepoFlag.WATCH])
annotation class RepoFlag{
    companion object {
        const val NORMAL = 0
        const val STAR = 1
        const val FORKED = 2
        const val WATCH = 3
    }

}