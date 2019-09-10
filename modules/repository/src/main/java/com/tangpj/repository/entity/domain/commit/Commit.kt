package com.tangpj.repository.entity.domain.commit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tangpj.github.db.DataTypeConverters
import com.tangpj.repository.entity.BaseEntity
import org.threeten.bp.LocalDateTime

@TypeConverters(DataTypeConverters::class)
@Entity
data class Commit(
        @PrimaryKey
        override val id: String,
        val message: String = "",
        val committerId: String ="",
        val committedDate: LocalDateTime = LocalDateTime.now(),
        val commentCount: Int = 0

) : BaseEntity(id)