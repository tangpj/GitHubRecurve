package tang.com.mvvmrecurve

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import tang.com.mvvmrecurve.api.GithubOauth2.Companion.launchOauthLogin
import tang.com.mvvmrecurve.databinding.ActivityMainBinding
import tang.com.recurve.widget.*

class MainActivity : AppCompatActivity() {

    private val adapter = ModulesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)


    }

    fun add(v: View){
        launchOauthLogin(this)
    }
}
