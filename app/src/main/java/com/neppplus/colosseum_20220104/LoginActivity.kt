package com.neppplus.colosseum_20220104

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivityLoginBinding
import com.neppplus.colosseum_20220104.utils.ContextUtil
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupEvents()
        setValues()


    }

    override fun setupEvents() {

        binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

//            서버에서 이메일 / 비번이 맞는 계정인지? 로그인 요청

            ServerUtil.postRequestLogIn(
                inputEmail,
                inputPw,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

//                    로그인 API를 호출하고 돌아온 상황.
//                    결과로 jsonObj 하나를 받아서 돌아온 상황
                        Log.d("화면에서의jsonObj", jsonObj.toString())

                        val code = jsonObj.getInt("code")

                        val msg = jsonObj.getString("message")
                        runOnUiThread {

                            if (code == 200) {

//                            연습문제, 로그인 성공시 그 사람의 닉네임을 추출해서
//                           " ~~님 환영합니다!" 토스트 출력하기
                                val dataObj = jsonObj.getJSONObject("data")
                                val userObj = dataObj.getJSONObject("user")
                                val nickname = userObj.getString("nick_name")

                                Toast.makeText(mContext, "${nickname}님 환영합니다", Toast.LENGTH_SHORT)
                                    .show()

//                                서버가 내내려준 토큰값도 추출
                               val token = dataObj.getString("token")
                                
//                                SharedPrefernces라는 공간에 토큰값 저장

                                ContextUtil.setToken(mContext, token)

//                                메인으로 이동
                                val myIntent = Intent(mContext, MainActivity::class.java)
                                startActivity(myIntent)

//                                로그인 화면 종료
                                finish()

                            } else {

//                            message String으로 실패 사유를 알려준다.
//                            JSON 파싱으로 추출해서 -> "로그인 실패" 대신 서버가 알려준 실패 사유를 띄우자.

                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()

                            }

                        }


                    }


                })

        }

    }

    override fun setValues() {

    }


}