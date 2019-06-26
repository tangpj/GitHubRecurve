package com.tangpj.repository.mapper

import com.tangpj.repository.BlodDetailQuery
import com.tangpj.repository.vo.FileContent

fun BlodDetailQuery.Data.getFileContent(expression: String): FileContent?{
    val temp = expression.split(".")
    val fileExt = if (temp.size > 1){
        temp[1]
    }else{
        ""
    }
    val asBlob = repository?.`object` as? BlodDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(
            id = asBlob.id,
            content = asBlob.text?: "",
            byteSize = asBlob.byteSize,
            fileExtensions = fileExt)
}