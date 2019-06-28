package com.tangpj.repository.mapper

import com.tangpj.repository.BlodDetailQuery
import com.tangpj.repository.vo.FileContent

fun BlodDetailQuery.Data.getFileContent(expression: String): FileContent?{

    val asBlob = repository?.`object` as? BlodDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(
            id = asBlob.id,
            content = asBlob.text?: "",
            byteSize = asBlob.byteSize,
            type = extOfType(expression))
}

fun BlodDetailQuery.Data.extOfType(expression: String): FileContent.Type{
    val temp = expression.split(".")
    if (temp.size <= 1){
       return FileContent.Type.DEFAULT
    }
    return when(temp[1]){
        "md" -> FileContent.Type.MARK_DOWN
        else -> FileContent.Type.DEFAULT
    }
}