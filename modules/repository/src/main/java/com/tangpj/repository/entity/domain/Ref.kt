package com.tangpj.repository.entity.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tangpj.github.entity.BaseEntity

@Entity
data class Ref(
        @PrimaryKey
        override val id: String,
        val name: String) : BaseEntity(id)