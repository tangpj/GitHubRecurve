package com.tangpj.github.domain

import androidx.room.Entity

@Entity
data class PageInfo(val hasNextPage: Boolean,
               val hasPreviousPage: Boolean,
               val startCursor: String,
               val endCursor: String)