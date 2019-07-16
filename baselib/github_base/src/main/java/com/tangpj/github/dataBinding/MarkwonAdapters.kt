package com.tangpj.github.dataBinding

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.tangpj.github.R
import io.noties.markwon.Markwon
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.recycler.MarkwonAdapter
import io.noties.markwon.recycler.SimpleEntry
import io.noties.markwon.recycler.table.TableEntry
import io.noties.markwon.recycler.table.TableEntryPlugin
import org.commonmark.ext.gfm.tables.TableBlock
import org.commonmark.node.FencedCodeBlock

class MarkwonAdapters{

    @BindingAdapter("md")
    fun RecyclerView.loadMarkdown(markdownStr: String? = null){
        markdownStr ?: return
        val markwon = markwon(context)
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        val adapter = MarkwonAdapter.builder(R.layout.item_md_default, R.id.text)
                .include(FencedCodeBlock::class.java, SimpleEntry.create(R.layout.item_md_code_bock, R.id.text))
                .include(TableBlock::class.java, TableEntry.create { builder -> builder
                        .tableLayout(R.layout.item_md_table, R.id.table_layout)
                        .tableLayoutIsRoot(R.layout.tv_table_entry_cell)
                }).build()

        adapter.setMarkdown(markwon, markdownStr)
    }

    private fun markwon(context: Context) =
            Markwon.builder(context)
                    .usePlugin(CorePlugin.create())
                    .usePlugin(GlideImagesPlugin.create(context))
                    .usePlugin(HtmlPlugin.create())
                    .usePlugin(TableEntryPlugin.create(context))
                    .build()
}