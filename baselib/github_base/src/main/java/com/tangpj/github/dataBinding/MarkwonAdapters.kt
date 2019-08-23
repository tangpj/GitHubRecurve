package com.tangpj.github.dataBinding

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.text.TextUtils
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.Target
import com.tangpj.github.R
import com.tangpj.github.utils.getActivity
import com.tangpj.github.utils.getDefaultRequestManager
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.image.svg.SvgMediaDecoder
import io.noties.markwon.recycler.MarkwonAdapter
import io.noties.markwon.recycler.SimpleEntry
import io.noties.markwon.recycler.table.TableEntry
import io.noties.markwon.recycler.table.TableEntryPlugin
import io.noties.markwon.urlprocessor.UrlProcessor
import io.noties.markwon.urlprocessor.UrlProcessorRelativeToAbsolute
import org.commonmark.ext.gfm.tables.TableBlock
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.SoftLineBreak

class MarkwonAdapters{

    @BindingAdapter("md")
    fun RecyclerView.loadMarkdown(markdownStr: String? = null){
        markdownStr ?: return

        val markwon = markwon(context)
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        val mdAdapter = MarkwonAdapter.builder(R.layout.item_md_default, R.id.text)
                .include(FencedCodeBlock::class.java, SimpleEntry.create(R.layout.item_md_code_bock, R.id.text))
                .include(TableBlock::class.java, TableEntry.create { builder -> builder
                        .tableLayout(R.layout.item_md_table, R.id.tl_md)
                        .textLayoutIsRoot(R.layout.tv_table_entry_cell)
                }).build()

        adapter = mdAdapter
        mdAdapter.setMarkdown(markwon, markdownStr)
    }


}


fun markwon(context: Context) : Markwon{
    return Markwon.builder(context)
            .usePlugin(CorePlugin.create())
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(ImagesPlugin.create(ImagesPlugin.ImagesConfigure {
                it.addMediaDecoder(SvgMediaDecoder.create(context.resources))
                it.addMediaDecoder(SvgMediaDecoder.create())
            }))
            .usePlugin(HtmlPlugin.create())
            .usePlugin(TableEntryPlugin.create(context))
            .usePlugin(object : AbstractMarkwonPlugin() {


                override fun configureVisitor(builder: MarkwonVisitor.Builder) {
//                        builder.on(FencedCodeBlock::class.java) { visitor, fencedCodeBlock ->
                    // we actually won't be applying code spans here, as our custom view will
                    // draw background and apply mono typeface
                    //
                    // NB the `trim` operation on literal (as code will have a new line at the end)
//                            val code = visitor.configuration().syntaxHighlight()
//                                    .highlight(fencedCodeBlock.info,
//                                            fencedCodeBlock.literal.trim { it <= ' ' })
//
//                            visitor.builder().append(code)
//                        }

                    builder.on(SoftLineBreak::class.java){ visitor, softLineBreak->
                        visitor.forceNewLine()
                    }

                }
            })
            .build()
}
