package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20220104.adapters.ReReplyAdapter
import com.neppplus.colosseum_20220104.databinding.ActivityViewReplyDetailBinding
import com.neppplus.colosseum_20220104.datas.ReplyData
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    lateinit var binding : ActivityViewReplyDetailBinding

    lateinit var mReplyData : ReplyData

    val mReReplyList = ArrayList<ReplyData>()
    lateinit var mReReplyAdapter : ReReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        binding.btnOk.setOnClickListener {

            val inputContent = binding.edtContent.text.toString()

//            서버에 대댓글 등록 API 호출

            ServerUtil.postRequestReReply(mContext, mReplyData.id,inputContent, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

//                    1.댓글 목록 새로고침
                    getReplyDetailFromServer()

                    runOnUiThread {
//                    2.입력칸 비워주기
                        binding.edtContent.setText("")



                    }

                }


            })

        }

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("reply")as ReplyData

        binding.txtSelectedSide.text ="(${mReplyData.selectedSide.title})"
        binding.txtWriterNickname.text = mReplyData.writer.nickname
        binding.txtContent.text = mReplyData.content

        getReplyDetailFromServer()

        mReReplyAdapter = ReReplyAdapter(mContext, R.layout.re_reply_list_item,mReReplyList)
        binding.reReplyLisView.adapter = mReReplyAdapter
    }

    fun getReplyDetailFromServer() {

        ServerUtil.getRequestReplyDetail(mContext,mReplyData.id, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                mReReplyList.clear()

                for( i in 0 until repliesArr.length()){

//                    [] => {} (JSONObject) 추출 -> ReplyData로 변환 -> 대댓글 목록에 추가 (최종목표)

                    mReReplyList.add( ReplyData.getReplyDataFromJson( repliesArr.getJSONObject(i) )  )

                }

                runOnUiThread {

                    mReReplyAdapter.notifyDataSetChanged()

                    //                        3. 리스트뷰를 맨 밑(20개가 있으면 19번: 갯수 - 1번째칸)으로 스크롤 해주기.
                    binding.reReplyLisView.smoothScrollToPosition(mReReplyList.size-1)

                }
            }

        })

    }

}