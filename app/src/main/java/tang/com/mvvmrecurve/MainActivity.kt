package tang.com.mvvmrecurve

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import tang.com.mvvmrecurve.databinding.ActivityMainBinding
import tang.com.recurve.widget.*

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
        stringCreate.setOnItemClickListener { _, e, creatorPosition ->
            Toast.makeText(this,"$e, creaotrPosition = " +
                    "$creatorPosition",Toast.LENGTH_SHORT).show()
        }

        val creator = SimpleExpandableCreator<String,String>(adapter,creatorType = 2)
        creator.setOnParentClickListener { _, parent, parentPosition, inCreatorPosition ->
            Toast.makeText(this,"$parent, parentPosition = " +
                    "$parentPosition, inCreatorPosition $inCreatorPosition",Toast.LENGTH_SHORT).show()
        }
        creator.setOnChildClickListener { _, child, childPosition, inCreatorPosition ->
            Toast.makeText(this,"$child, childPosition = " +
                    "$childPosition, inCreatorPosition $inCreatorPosition",Toast.LENGTH_SHORT).show()
        }
        adapter.addCreator(creator)
        val linked = LinkedHashMap<String,MutableList<String>>()

        linked["2222"] = mutableListOf("4","5","6")
        linked["3333"] = mutableListOf("7","8")
        creator.setDataList(linked)
        val stringCreate1 = stringCreator(adapter,1)
        stringCreate1.setOnItemClickListener { _, e, creatorPosition ->
            Toast.makeText(this,"$e, creaotrPosition = " +
                    "$creatorPosition",Toast.LENGTH_SHORT).show()
        }
        stringCreate1.setDataList(mutableListOf("99999","8888888","666666"))
        adapter.addCreator(stringCreate1)


    }

    fun add(v: View){
        val stringCreate1 = stringCreator(adapter,1)
        stringCreate1.setDataList(mutableListOf("4","5","6"))
        adapter.addCreator(stringCreate1)
    }
}
