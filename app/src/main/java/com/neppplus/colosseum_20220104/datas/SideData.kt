package com.neppplus.colosseum_20220104.datas

import org.json.JSONObject
import java.io.Serializable

class SideData : Serializable {

    var id =0
    var title = ""
    var voteCount = 0

    companion object {

        fun getSideDataFromJson( jsonObj: JSONObject) : SideData{

            val sideData = SideData()

//            기본값 -> jsonObj를 이용해서 파싱된 값으로 대입
            sideData.id = jsonObj.getInt("id")
            sideData.title = jsonObj.getString("title")
            sideData.voteCount = jsonObj.getInt("vote_count")

            return sideData

        }

    }

}