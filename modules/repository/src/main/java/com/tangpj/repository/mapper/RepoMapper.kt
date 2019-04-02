package com.tangpj.repository.mapper

import com.tangpj.repository.fragment.LanguageDto
import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface RepoMapper{

    @Mappings(
            Mapping(source = "repoDto.id", target = "id"),
            Mapping(source = "repoDto.name", target = "name"),
            Mapping(source = "repoDto.stargazers.totalCount", target = "stars"),
            Mapping(source = "repoDto.forks.totalCount", target = "forks"),
            Mapping(target = "fullName",
                    expression = "java(repoDto.getOwner().getLogin() + \"/\" + repoDto.getName())"),
            Mapping(source = "languageDto.name", target = "language"),
            Mapping(source = "languageDto.color", target = "languageColor"))
    fun from(repoDto: RepoDto, languageDto: LanguageDto?): RepoVo
}