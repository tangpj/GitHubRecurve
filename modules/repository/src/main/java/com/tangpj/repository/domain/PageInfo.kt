package com.tangpj.repository.domain


class PageInfo(val hasNextPage: Boolean,
               val hasPreviousPage: Boolean,
               val startCursor: String,
               val endCursor: String){

}