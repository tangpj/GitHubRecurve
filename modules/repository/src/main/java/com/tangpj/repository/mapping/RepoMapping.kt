package com.tangpj.repository.mapping

import com.tangpj.github.StartReposioriesQuery

fun StartReposioriesQuery.Data.mappingToRepo(){
    this.user()
}