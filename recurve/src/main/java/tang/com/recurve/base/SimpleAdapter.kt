package tang.com.recurve.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/10.
 */
abstract class SimpleAdapter<E,ViewHolder: SimpleAdapter.SimpleViewHolder> : RecyclerView.Adapter<ViewHolder>() {

    var dateList: List<E>? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    abstract fun onBind(holder: ViewHolder,position: Int)

    abstract fun onCreateHolder(parent: ViewGroup?, viewType: Int): ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return dateList?.size ?: 0
    }

    open class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

