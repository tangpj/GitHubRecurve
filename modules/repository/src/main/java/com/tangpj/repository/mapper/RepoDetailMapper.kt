package com.tangpj.repository.mapper

import com.tangpj.repository.BlodDetailQuery
import com.tangpj.repository.vo.FileContent

fun BlodDetailQuery.Data.getFileContent(): FileContent?{
    val asBlob = repository?.`object` as? BlodDetailQuery.AsBlob
    asBlob?: return null
    return FileContent(id = asBlob.id, content = asBlob.text?: "", byteSize = asBlob.byteSize)
}