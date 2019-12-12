package com.kot.kotadapter.adpter.base

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import com.kot.kotadapter.adpter.models.KotAdapterModel

open class KotAdapterBuilder<M : Any> {
    private val lModels  = mutableListOf<KotAdapterModel>()
    private var listItem = mutableListOf<M>()
    private var onSelectionChanged : ((isSelected : Boolean)-> Unit)? =null
    private var onActionItemClicked : ((item : MenuItem)-> Unit)? =null
    private var onActionMenuCreated : ((menu : Menu)-> Unit)? =null
    private var onActionModeFinished : (()-> Unit)?=null
    private @MenuRes
    var menuResId : Int =0
    private lateinit  var activity: Activity


    constructor(){}

    fun with(activity: Activity ) = apply { this.activity = activity }
    fun addModel(model : KotAdapterModel) = apply { lModels.add(model) }
    fun addModel(@LayoutRes layout : Int , viewHolder : Class<*> , itemType : Class<*>) =apply {
        lModels.add(
            KotAdapterModel(
                layout,
                viewHolder,
                itemType
            )
        )
    }
    fun setItems(mList : MutableList<M>)= apply {
        this.listItem = mList
    }

    fun setOnActionModeFinishListener(onActionModeFinished : (()-> Unit)?)=apply { this.onActionModeFinished = onActionModeFinished }
    fun setOnItemSelectionChangeListener(onSelectionChanged : ((isSelected : Boolean)-> Unit)?) = apply { this.onSelectionChanged = onSelectionChanged }
    fun setOnActionItemClickedListener(onActionItemClicked : ((item : MenuItem)-> Unit)?)=apply { this.onActionItemClicked = onActionItemClicked }
    fun setOnActionMenuCreatedListener(onActionMenuCreated : ((menu : Menu)-> Unit)?)= apply { this.onActionMenuCreated =onActionMenuCreated }
    fun menuRes(@MenuRes menuResId : Int) = apply { this.menuResId= menuResId }
    fun build() : KotRecyclerAdapter<M> {
         return   KotRecyclerAdapter(activity , listItem,lModels , menuResId , onActionMenuCreated, onActionItemClicked, onSelectionChanged, onActionModeFinished)
    }
}