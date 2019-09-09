package com.tangpj.repository.mapper

import com.tangpj.repository.ApolloBlobDetailQuery
import com.tangpj.repository.ApolloFileTreeQuery
import com.tangpj.repository.ApolloRepoDetailQuery
import com.tangpj.repository.entity.file.FileContent
import com.tangpj.repository.entity.file.FileItem
import com.tangpj.repository.entity.file.FileType
import com.tangpj.repository.vo.RepoDetail


fun ApolloRepoDetailQuery.Data.getRepoDetail() : RepoDetail?{
    val repoDetailDto = repository?.fragments?.repoDetailDto ?: return null
    val owner = repoDetailDto.owner.fragments.ownerDto.getOwner()
    return RepoDetail(
            id = repoDetailDto.id,
            owner = owner,
            name = repoDetailDto.name,
            description = repoDetailDto.description ?:  "",
            stars = repoDetailDto.stargazers.totalCount,
            forks = repoDetailDto.forks.totalCount,
            watchers = repoDetailDto.watchers.totalCount,
            url = repoDetailDto.url,
            sshUrl = "",
            topics = repoDetailDto.repositoryTopics.nodes?.map {
                it.topic.name } ?: emptyList()
    )
}

fun ApolloFileTreeQuery.Data.getFileItems() : List<FileItem>{
    val tree = repository?.gitObject as? ApolloFileTreeQuery.AsTree
    val groupList = tree?.entries?.map {
        FileItem(
                id = it.gitObject?.id ?: "",
                name = it.name,
                type = it.type)
    }?.sortedBy { it.name }?.groupBy {
        it.type
    }
    groupList ?: return emptyList()
    val count = groupList.entries.map { it.value.size }.reduce { acc, i -> acc + i }
    val result =  ArrayList<FileItem>(count)
    groupList[FileType.TREE]?.let { result.addAll(it) }
    groupList[FileType.BLOB]?.let { result.addAll(it) }
    groupList.filter { it.key != FileType.TREE && it.key != FileType.BLOB }.forEach {
        result.addAll(it.value)
    }
    return result
}

fun ApolloBlobDetailQuery.Data.getFileContent(expression: String): FileContent?{

    val asBlob = repository?.`object` as? ApolloBlobDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(
            id = asBlob.id,
            content = asBlob.text ?: "",
            byteSize = asBlob.byteSize,
            type = extOfType(expression))
}

fun ApolloBlobDetailQuery.Data.extOfType(expression: String): FileContent.Type{
    val temp = expression.split(".")
    if (temp.size <= 1){
       return FileContent.Type.DEFAULT
    }
    return when(temp[1]){
        "md" -> FileContent.Type.MARK_DOWN
        else -> FileContent.Type.DEFAULT
    }
}