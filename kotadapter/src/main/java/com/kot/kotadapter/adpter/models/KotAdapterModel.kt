package com.kot.kotadapter.adpter.models

import androidx.annotation.LayoutRes

data class KotAdapterModel(
   @LayoutRes val layout : Int,
    val mViewHolder : Class<*>,
   val mItemType : Class<*>
)