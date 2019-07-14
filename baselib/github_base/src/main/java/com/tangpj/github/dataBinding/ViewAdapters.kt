package com.tangpj.github.dataBinding

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.html.HtmlPlugin


@BindingAdapter("md")
fun RecyclerView.loadMarkdown(markdownStr: String? = null){
    markdownStr ?: return

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

private fun markwon(context: Context) =
        Markwon.builder(context)
                .usePlugin(CorePlugin.create())
                .usePlugin(Pica)
