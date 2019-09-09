package com.tangpj.repository.entity.commit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tangpj.github.db.DataTypeConverters
import com.tangpj.repository.entity.BaseEntity
import com.tangpj.repository.entity.actor.git.Committer
import java.time.LocalDateTime
import java.util.*

@TypeConverters(DataTypeConverters::class)
@Entity
data class Commit(
        @PrimaryKey
        override val id: String,
        val message: String,
        val committerId: String,
        val committedDate: LocalDateTime,
        val commentCount: String

) : BaseEntity(id)