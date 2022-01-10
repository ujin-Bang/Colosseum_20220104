package com.neppplus.colosseum_20220104

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20220104.databinding.ActivityViewTopicDetailBinding
import com.neppplus.colosseum_20220104.datas.TopicData
import com.neppplus.colosseum_20220104.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding : ActivityViewTopicDetailBinding

    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        binding.txtTopicTitle.text = mTopicData.title

        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

//        현재 진행상황 조회API 호출해 보자. -> 토론 진영 목록 / 몇표 획득?
        getTopicDetailFromServer()
    }
    fun getTopicDetailFromServer(){

    ServerUtil.getRequestTopicDetail(mContext, mTopicData.id,object :ServerUtil.JsonResponseHandler{
        override fun onResponse(jsonObj: JSONObject) {

            val dataObj = jsonObj.getJSONObject("data")
            val topicObj = dataObj.getJSONObject("topic")

//            topicObj -> 토론 주제에 대한 정보가 담긴 JSONObject -> TopicData 변환 함수의 재료로 사용.
           mTopicData = TopicData.getTopicDatFromJson(topicObj)

            runOnUiThread {

                refreshUI()


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
        binding.txtSideVoteCount01.text = "${mTopicData.sideList[1].voteCount}표"
    }

}