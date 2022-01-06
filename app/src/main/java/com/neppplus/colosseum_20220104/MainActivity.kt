package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.databinding.ActivityMainBinding
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

    }

    override fun setValues() {

//        연습 - 내 정보 API 호출 -> 닉네임 추출 /UI 반영
//        getMyInfoFromServer()

//        실제 - 메인 화면의 데이터 받아오기 -> 토론주제 목록 -> 리스트뷰로 표기

    }

//    fun getMyInfoFromServer(){
//
//        ServerUtil.getRequestMyInfo(mContext, object :ServerUtil.JsonResponseHandler{
//            override fun onResponse(jsonObj: JSONObject) {
//
//                val dataObj = jsonObj.getJSONObject("data")
//                val userObj = dataObj.getJSONObject("user")
//                val nickname =userObj.getString("nick_name")
//
//                runOnUiThread {
//
//                    binding.txtUserNickname.text = nickname
//
//                }
//
//            }
//
//
//        })
//
//    }


}