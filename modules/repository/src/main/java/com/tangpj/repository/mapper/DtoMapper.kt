package com.tangpj.repository.mapper

import com.tangpj.repository.fragment.LanguageDto
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo

fun RepoDto.mapRepoVo(mapper: RepoMapper, languageDto: LanguageDto?): RepoVo{
    return mapper.from(this, languageDto)
}

