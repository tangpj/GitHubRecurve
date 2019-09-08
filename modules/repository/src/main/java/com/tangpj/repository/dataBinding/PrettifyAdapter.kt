package com.tangpj.repository.dataBinding

import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import com.prettifier.pretty.PrettifyWebView
import com.tangpj.repository.entry.file.FileContent
import org.markdown4j.Markdown4jProcessor

/**
 *
 * FileContent辅助加载工具
 *
 * @className:
 * @author: tang
 * @createTime: 2019-08-23 16:28
 */

@BindingAdapter(value = ["fileContent"])
fun PrettifyWebView.loadFileContent(fileContent: FileContent?){
    fileContent ?: return
    if (fileContent.type == FileContent.Type.MARK_DOWN){
        onSetMdText(this, fileContent.content)
    }else{
        onSetCode(this, fileContent.content)
    }
}
private fun onSetMdText(webView: PrettifyWebView, text: String) {
    webView.visibility = View.VISIBLE
    Markdown4jProcessor().registerPlugins()
    val markdownStr = Markdown4jProcessor().process(text)
    //todo theme可配置
    webView.setGithubContentWithReplace(markdownStr)
}

private fun onSetCode(webView: PrettifyWebView, @NonNull text: String) {
    webView.visibility = View.VISIBLE
    //todo isWrap可配置
    webView.setSource(text, true)
}

private fun onSetImageUrl(webView: PrettifyWebView, url: String, isSvg: Boolean) {
    webView.loadImage(url, isSvg)
    webView.visibility = View.VISIBLE
}