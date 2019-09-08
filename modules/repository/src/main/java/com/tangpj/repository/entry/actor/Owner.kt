package com.tangpj.repository.entry.actor

import androidx.room.Entity

@Entity(primaryKeys = ["id","login"])
data class Owner(
        override
        val id : String,
        override
        val login: String,
        override
        val avatarUrl: String? = null) : Actor(id, login, avatarUrl)