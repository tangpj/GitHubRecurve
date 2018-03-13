package tang.com.recurve.widget

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tang.com.recurve.databinding.ItemTextBinding

/**
 * Created by tang on 2018/3/13.
 * 可以通过StringCreator快速创建字符串列表adapter
 */
class StringCreator(adapter: ModulesAdapter): ItemCreator<String, StringCreator.StringHolder>(adapter) {

    override fun onCreateItemView(parent: ViewGroup): StringHolder
            = StringHolder(ItemTextBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindItemView(itemHolder: StringHolder, e: String, inCreatorPosition: Int) {
        itemHolder.binding.text = e
    }

    class StringHolder(val binding: ItemTextBinding): RecyclerView.ViewHolder(binding.root)
}