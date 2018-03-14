package tang.com.mvvmrecurve

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import tang.com.mvvmrecurve.databinding.ActivityMainBinding
import tang.com.recurve.widget.ArrayCreator
import tang.com.recurve.widget.ModulesAdapter
import tang.com.recurve.widget.stringCreator

class MainActivity : AppCompatActivity() {

    private val adapter = ModulesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        binding.list.layoutManager = LinearLayoutManager(this)
        val stringCreate = stringCreator(adapter)
        binding.list.adapter = adapter
        adapter.addCreator(stringCreate)
        stringCreate.setDataList(mutableListOf("1","2","3"))


    }

    fun add(v: View){
        val stringCreate1 = stringCreator(adapter,1)
        stringCreate1.setDataList(mutableListOf("4","5","6"))
        adapter.addCreator(stringCreate1)
    }
}
