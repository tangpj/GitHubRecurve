package com.tangpj.github.entity.domain

import androidx.room.Entity

@Entity
data class PageInfo(val hasNextPage: Boolean,
               val hasPreviousPage: Boolean,
               val startCursor: String,
               val endCursor: String)