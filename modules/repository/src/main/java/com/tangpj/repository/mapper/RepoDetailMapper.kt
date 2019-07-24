package com.tangpj.repository.mapper

import com.tangpj.repository.BlobDetailQuery
import com.tangpj.repository.FileTreeQuery
import com.tangpj.repository.vo.FileContent
import com.tangpj.repository.vo.FileItem

fun FileTreeQuery.Data.getFileItems() : List<FileItem>{
    val tree = repository?.gitObject as? FileTreeQuery.AsTree
    return tree?.entries?.map { FileItem(
            id = it.gitObject?.id ?: "",
            name = it.name,
            type = it.type) } ?: listOf()
}

fun BlobDetailQuery.Data.getFileContent(expression: String): FileContent?{

    val asBlob = repository?.`object` as? BlobDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(
            id = asBlob.id,
            content = asBlob.text?: "",
            byteSize = asBlob.byteSize,
            type = extOfType(expression))
}

fun BlobDetailQuery.Data.extOfType(expression: String): FileContent.Type{
    val temp = expression.split(".")
    if (temp.size <= 1){
       return FileContent.Type.DEFAULT
    }
    return when(temp[1]){
        "md" -> FileContent.Type.MARK_DOWN
        else -> FileContent.Type.DEFAULT
    }
}