package com.tangpj.repository.mapper

import com.tangpj.repository.entity.author.CommitAuthor


fun CommitAuthor.getApolloAuthor(): com.tangpj.repository.type.CommitAuthor =
        com.tangpj.repository.type.CommitAuthor.builder()
                .id(id)
                .emails(listOf(email))
                .build()
