package tang.com.oauth.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tang.com.oauth.R
import tang.com.oauth.databinding.ActivityOauth2Binding

class Oauth2Activity: AppCompatActivity(){

    lateinit var binding: ActivityOauth2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_oauth2)

    }
}