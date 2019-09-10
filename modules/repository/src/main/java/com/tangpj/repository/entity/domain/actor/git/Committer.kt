package com.tangpj.repository.entity.domain.actor.git

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tangpj.github.db.DataTypeConverters
import org.threeten.bp.LocalDateTime

@TypeConverters(DataTypeConverters::class)
@Entity
class Committer(
        @PrimaryKey
        override val id: String,
        override val name: String = "",
        override val email: String = "",
        override val avatarUrl: String = "",
        override val date: LocalDateTime = LocalDateTime.now())
    : GitActor(
        id = id,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        date = date)
