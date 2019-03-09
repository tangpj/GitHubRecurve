package com.tangpj.repository.mapper

import com.tangpj.repository.fragment.RepoDto
import com.tangpj.repository.vo.RepoVo
import org.mapstruct.MapMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface RepoMapper{

    fun from (repoDto: RepoDto): RepoVo

}