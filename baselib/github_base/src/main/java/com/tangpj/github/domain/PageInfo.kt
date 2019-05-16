package com.tangpj.github.domain

import androidx.room.Entity

@Entity
class PageInfo(val hasNextPage: Boolean,
               val hasPreviousPage: Boolean,
               val startCursor: String,
               val endCursor: String)