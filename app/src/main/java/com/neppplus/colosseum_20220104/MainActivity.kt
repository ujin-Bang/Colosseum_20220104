package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivityMainBinding
import com.neppplus.colosseum_20220104.utils.ServerUtil

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvents()
        setValues()


    }
    override fun setupEvents() {
        
        binding.btnLogin.setOnClickListener { 
            
            val inputEmail = binding.edtEmail.toString()
            val inputPw = binding.edtPassword.toString()
            
//            서버에서 이메일 / 비번이 맞는 계정인지? 로그인 요청

            ServerUtil.postRequestLogIn(inputEmail,inputPw)

        }

    }

    override fun setValues() {

    }


}