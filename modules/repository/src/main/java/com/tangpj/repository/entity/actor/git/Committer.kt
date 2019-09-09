package com.tangpj.repository.entity.actor.git

import androidx.room.Entity
import java.util.Date

@Entity
class Committer(
        override val id: String,
        override val name: String,
        override val email: String,
        override val avatarUrl: String,
        override val date: Date)
    : GitActor(
        id = id,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        date = date)
