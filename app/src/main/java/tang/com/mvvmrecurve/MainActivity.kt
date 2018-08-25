package tang.com.mvvmrecurve

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import tang.com.mvvmrecurve.databinding.ActivityMainBinding
import tang.com.oauth2.GithubOauth2
import tang.com.recurve.widget.ModulesAdapter

class MainActivity : AppCompatActivity() {

    private val adapter = ModulesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)


    }

    fun add(v: View){
        GithubOauth2.launchOauthLogin(this)
    }
}
