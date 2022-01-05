package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivitySignUpBinding
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {
        
        binding.btnEmailCheck.setOnClickListener { 
            
            val inputEmail = binding.edtEmail.text.toString()
            
//            서버에 중복 확인 기능 요청 -> ServerUtil 이용

            ServerUtil.getRequestDuplCheck("EMAIL",inputEmail,object :ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    runOnUiThread {

                        if (code ==200){

//                        사용해도 좋다.
                            binding.txtEmailCheckResult.text = "사용해도 좋습니다."

                        }
                        else {
//                        사용하면 안됨.
                            binding.txtEmailCheckResult.text ="중복된 이메일입니다. 다른아이디 입력!!"


                        }
                    }

                }

            })
            
            
        }

        binding.btnOk.setOnClickListener {

//            입력한 이메일 / 비번 /닉네임을 변수에 담아두자

            val inputEmail = binding.edtEmail.text.toString()

            val inputpw = binding.edtPassword.text.toString()

            val inputNickname = binding.edtNickname.text.toString()

//            서버의 회원가입 기능에 전달(Request) -> 돌아온 응답 대응(Response)

            ServerUtil.putRequestSignUp(inputEmail, inputpw, inputNickname, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    if(code == 200) {

//                     가입한 사람의 닉네임을 추출 -> 토스트로 환영 메세지
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")

                        val nickname = userObj.getString("nick_name")

//                        토스트로 환영 메시지 + 회원가입 종료
                        runOnUiThread {

                            Toast.makeText(mContext, "${nickname}님 회원가입을 축하합니다", Toast.LENGTH_SHORT)
                                .show()

                            finish()

                        }

                    }
                    else {

//                        실패 -> 서버가 알려주는 "message"에 담긴 실패 사유를 토스트로
                        val message = jsonObj.getString("message")

                        runOnUiThread {

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        }

                    }

                }

            })
        }

    }

    override fun setValues() {

    }


}