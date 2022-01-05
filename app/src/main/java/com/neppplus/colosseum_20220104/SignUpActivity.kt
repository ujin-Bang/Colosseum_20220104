package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

        binding.btnOk.setOnClickListener {

//            입력한 이메일 / 비번 /닉네임을 변수에 담아두자

            val inputEmail = binding.edtEmail.text.toString()

            val inputpw = binding.edtPassword.text.toString()

            val inputNickname = binding.edtNickname.text.toString()

//            서버의 회원가입 기능에 전달(Request) -> 돌아온 응답 대응(Response)
        }

    }

    override fun setValues() {

    }


}