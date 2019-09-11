package com.tangpj.github.dataBinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tangpj.github.utils.getTimeline
import org.threeten.bp.LocalDateTime


@BindingAdapter("timeline")
fun TextView.setTimeline(dateTime: LocalDateTime){
    text = dateTime.getTimeline(context)
}
