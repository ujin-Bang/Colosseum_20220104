package com.neppplus.colosseum_20220104.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.neppplus.colosseum_20220104.datas.TopicData

class TopicAtapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<TopicData>): ArrayAdapter<TopicData>(mContext, resId, mList) {
}