package com.tangpj.repository.mapper

import com.tangpj.repository.fragment.LanguageDto
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo
import org.mapstruct.factory.Mappers

fun RepoDto.mapRepoVo(languageDto: LanguageDto?): RepoVo{
    val repoMapper: RepoMapper = Mappers.getMapper(RepoMapper::class.java)
    return repoMapper.from(this, languageDto)
}





