package com.neppplus.colosseum_20220104.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData: Serializable {

//    생성자 : 기본 생성자 유지
//    멤버변수만 따로 추가 -> JSON 파싱해서 변수에 채워넣자.
//    멤버변수 : 서버의 데이터를 보고 -> 담아주는 용도의 변수들로 만들자.

    var id = 0 //Int가 들어올 자리라는 표식
    var title = "" // String이 들어올 자리
    var imageURL = ""

    var replyCount = 0

//    토론 주제의 하위 목록 -> 진영선택( SideData) 목록(ArrayList)
    var sideList = ArrayList<SideData>()

    companion object {

//        JSONObject -> TopicData 형태로 변환해주는 함수 제작
//        다른 화면들에서는 이 함수를 끌어다 사용

        fun getTopicDatFromJson(jsonObj: JSONObject) : TopicData{

            val resultTopicData = TopicData()
            resultTopicData.id = jsonObj.getInt("id")
            resultTopicData.title = jsonObj.getString("title")
            resultTopicData.imageURL = jsonObj.getString("img_url")

            resultTopicData.replyCount = jsonObj.getInt("reply_count")

//            토론 주제 파싱 -> JSONArray 등장-> 선택진영 목록

            val sidesArr =jsonObj.getJSONArray("sides")

            for (i in 0 until sidesArr.length() ) {

                val sideObj = sidesArr.getJSONObject(i)

                val sideData = SideData.getSideDataFromJson(sideObj)

                resultTopicData.sideList.add(sideData)



            }

            return resultTopicData

        }

    }

}