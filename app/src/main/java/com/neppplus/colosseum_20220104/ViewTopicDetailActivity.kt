package com.neppplus.colosseum_20220104

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20220104.adapters.ReplyAdapter
import com.neppplus.colosseum_20220104.databinding.ActivityViewTopicDetailBinding
import com.neppplus.colosseum_20220104.datas.ReplyData
import com.neppplus.colosseum_20220104.datas.TopicData
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding : ActivityViewTopicDetailBinding

    lateinit var mTopicData : TopicData

    val mReplyList = ArrayList<ReplyData>()

    lateinit var mReplyAdapter : ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

        binding.btnWriteReply.setOnClickListener {

            val myIntent = Intent(mContext, EditReplyActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnVote01.setOnClickListener {

//            첫 진영에 투표 -> 서버 API 호출 -> 이 화면 새로고침

            ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")
                    if(code == 200) {

//                        득표 수를 서버에서 다시 받아오자.(새로 고침)
//                        득표 수? -> 토론 상세 조회 -> 선택진영 목록 -> 특표수 새로 파싱
                        getTopicDetailFromServer()
                    }
                    else {

                        val message = jsonObj.getString("message")

                        runOnUiThread {

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        }
                    }
                }

            })

        }

        binding.btnVote02.setOnClickListener {

            ServerUtil.postRequestVote(mContext, mTopicData.sideList[1].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    getTopicDetailFromServer()
                }

            })
        }

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        binding.txtTopicTitle.text = mTopicData.title

        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

//        현재 진행상황 조회API 호출해 보자. -> 토론 진영 목록 / 몇표 획득?
        getTopicDetailFromServer()

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.replyListView.adapter = mReplyAdapter
    }
    fun getTopicDetailFromServer(){

    ServerUtil.getRequestTopicDetail(mContext, mTopicData.id,"NEW", object :ServerUtil.JsonResponseHandler{
        override fun onResponse(jsonObj: JSONObject) {

            val dataObj = jsonObj.getJSONObject("data")
            val topicObj = dataObj.getJSONObject("topic")

//            topicObj -> 토론 주제에 대한 정보가 담긴 JSONObject -> TopicData 변환 함수의 재료로 사용.
           mTopicData = TopicData.getTopicDatFromJson(topicObj)

            runOnUiThread {

                refreshUI()

            }
//            댓글 목록 별도 파싱
            val repliesArr = topicObj.getJSONArray("replies")

//            기존에 받아온 댓글은 전부 삭제.
            mReplyList.clear()
            for(i in 0 until repliesArr.length()){

                val replyObj = repliesArr.getJSONObject(i)

                val replyData = ReplyData.getReplyDataFromJson(replyObj)

                mReplyList.add( replyData)
            }

//            서버가 더 늦게 끝났다면? 리스트뷰 내용 변경됨.
            runOnUiThread {
                mReplyAdapter.notifyDataSetChanged()
            }

        }


    })

    }

    fun refreshUI() {
//        mTopicData가 변경되었으면 -> 새로 반영해달라.

        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

        binding.txtReplyCount.text = "댓글 갯수: ${mTopicData.replyCount}"

        binding.txtSideTitle01.text = mTopicData.sideList[0].title
        binding.txtSideTitle02.text = mTopicData.sideList[1].title
        binding.txtSideVoteCount01.text = "${mTopicData.sideList[0].voteCount}표"
        binding.txtSideVoteCount02.text = "${mTopicData.sideList[1].voteCount}표"
    }

}