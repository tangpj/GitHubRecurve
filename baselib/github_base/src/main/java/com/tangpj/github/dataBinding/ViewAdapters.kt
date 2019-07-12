package com.tangpj.github.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.html.HtmlPlugin


@BindingAdapter("md")
fun TextView.loadMarkdown(markdownStr: String? = null){
    markdownStr ?: return
//    val markwon = Markwon.create(context)
//    val node = markwon.parse(markdownStr)
//    val spanned = markwon.render(node)
//    markwon.setParsedMarkdown(this, spanned)

    val markwon = Markwon.builder(context)
            .usePlugin(HtmlPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configure(registry: MarkwonPlugin.Registry) {
                    registry.require(HtmlPlugin::class.java) {
                    }
                }
            })
            .build()
    markwon.setMarkdown(this, markdownStr)
}