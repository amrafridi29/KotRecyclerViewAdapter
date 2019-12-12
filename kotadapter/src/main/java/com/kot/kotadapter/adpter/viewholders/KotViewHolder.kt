package com.kot.kotadapter.adpter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kot.kotadapter.adpter.base.KotRecyclerAdapter

abstract class KotViewHolder<M : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(element: M, adapter : KotRecyclerAdapter<M>)
}