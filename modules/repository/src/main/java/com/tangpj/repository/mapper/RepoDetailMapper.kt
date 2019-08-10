package com.tangpj.repository.mapper

import com.tangpj.repository.ApolloBlobDetailQuery
import com.tangpj.repository.ApolloFileTreeQuery
import com.tangpj.repository.vo.FileContent
import com.tangpj.repository.vo.FileItem

fun ApolloFileTreeQuery.Data.getFileItems() : List<FileItem>{
    val tree = repository?.gitObject as? ApolloFileTreeQuery.AsTree
    return tree?.entries?.map { FileItem(
            id = it.gitObject?.id ?: "",
            name = it.name,
            type = it.type) } ?: listOf()
}

fun ApolloBlobDetailQuery.Data.getFileContent(expression: String): FileContent?{

    val asBlob = repository?.`object` as? ApolloBlobDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(
            id = asBlob.id,
            content = asBlob.text?: "",
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