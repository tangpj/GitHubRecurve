package com.tangpj.repository.mapper

import com.tangpj.repository.ApolloRefsQuery
import com.tangpj.repository.entity.domain.Ref

fun ApolloRefsQuery.RefsDto.getLocalPageInfo() =
        pageInfo.fragments.pageInfoDto.mapperToLocalPageInfo()

fun ApolloRefsQuery.RefsDto.getRefs() : List<Ref>{
    return nodes?.map {
        Ref(it.id, it.name)
    } ?: emptyList()
}