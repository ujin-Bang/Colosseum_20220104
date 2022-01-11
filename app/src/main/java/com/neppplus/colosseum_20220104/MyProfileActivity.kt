package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= DataBindingUtil.setContentView(this,R.layout.activity_my_profile)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }


}