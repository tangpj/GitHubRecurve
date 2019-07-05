package com.tangpj.repository.utils

import android.webkit.WebView
import androidx.databinding.BindingAdapter
import com.tangpj.repository.vo.FileContent
import org.markdown4j.Markdown4jProcessor

@BindingAdapter("fileContent")
fun WebView.loadFileContent(fileContent: FileContent){
    val html = if (fileContent.type == FileContent.Type.MARK_DOWN){
        Markdown4jProcessor().process(fileContent.content)
    }else{
        fileContent.content
    }
    loadData(html, "text/html", "UTF-8")
}
