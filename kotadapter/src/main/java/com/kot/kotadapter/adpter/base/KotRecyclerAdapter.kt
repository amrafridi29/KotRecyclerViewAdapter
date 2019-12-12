package com.kot.kotadapter.adpter.base

import android.app.Activity
import android.util.SparseBooleanArray
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.MenuRes
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.kot.kotadapter.adpter.actionmode.KotActionModeCallback
import com.kot.kotadapter.adpter.imp.Searchable
import com.kot.kotadapter.adpter.imp.Selectable
import com.kot.kotadapter.adpter.models.KotAdapterModel
import com.kot.kotadapter.adpter.viewholders.KotViewHolder


class KotRecyclerAdapter <M : Any> : RecyclerView.Adapter<KotViewHolder<M>> , Filterable{


    var listItems : MutableList<M>
    var originalListItem = mutableListOf<M>()
    val models : MutableList<KotAdapterModel>
    private val selectedItems : SparseBooleanArray = SparseBooleanArray()
    private val actionMode = KotActionModeCallback()
    private var onSelectionChanged : ((isSelected : Boolean)-> Unit)? =null
    private var onActionItemClicked : ((item : MenuItem)-> Unit)? =null
    private var onActionMenuCreated : ((menu : Menu)-> Unit)? =null
    private var onActionModeFinished : (()-> Unit)?=null

    private  var activity: Activity
    private @MenuRes
    var menuResId : Int =0

    constructor(activity: Activity, listItems: MutableList<M>, models : MutableList<KotAdapterModel>,
                @MenuRes menuResId: Int,
                onActionMenuCreated : ((menu : Menu)-> Unit)?,
                onActionItemClicked : ((item : MenuItem)-> Unit)?,
                onSelectionChanged : ((isSelected : Boolean)-> Unit)?,
                onActionModeFinished : (()-> Unit)?) : super(){
        this.activity = activity
        this.listItems = listItems
        this.originalListItem.addAll(this.listItems)
        this.models = models
        this.menuResId = menuResId
        this.onActionMenuCreated = onActionMenuCreated
        this.onActionItemClicked = onActionItemClicked
        this.onSelectionChanged = onSelectionChanged
        this.onActionModeFinished = onActionModeFinished
        notifyDataSetChanged()

    }



    override fun getItemCount() = listItems.size

    override fun getItemViewType(position: Int): Int {
        val item  = (listItems.get(position))
        val classType = item::class.java

        for(i in 0 until models.size){
            if(models[i].mItemType == classType){
                return i
            }
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val lModel = models.get(viewType)
        val view = inflater.inflate(lModel.layout, parent, false)
        val mClass = lModel.mViewHolder
        return mClass.getConstructor(View::class.java).newInstance(view) as KotViewHolder<M>

    }

    override fun onBindViewHolder(holder: KotViewHolder<M>, position: Int) {
        holder.bindData(listItems[position], this)
    }

    fun setItems(list: MutableList<M>) {
        this.listItems.clear()
        this.originalListItem.clear()
        this.listItems.addAll(list)
        this.originalListItem.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(item: M) {
        this.originalListItem.clear()
        this.listItems.add(item)
        this.originalListItem.addAll(listItems)

        notifyDataSetChanged()
    }

    fun search(text : String?){
        filter.filter(text)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                listItems.clear()
                if(constraint.isNullOrBlank()){
                    listItems.addAll(originalListItem)
                }else{
                    val searchResult = originalListItem.filter {
                        if(it is Searchable){
                            it.getSearchCriteria().contains(constraint , ignoreCase = true)
                        } else{
                            true
                        }
                    }
                    listItems.addAll(searchResult)
                }
                return filterResults.also {
                    it.values = listItems
                }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    fun setActionModeMenu(@MenuRes menuResId : Int){
        this.menuResId =menuResId

    }

    fun setOnActionItemClicked(onActionItemClicked : ((item : MenuItem)-> Unit)?) {
        this.onActionItemClicked = onActionItemClicked

    }

    fun setOnActionMenuCreated(onActionMenuCreated  : ((menu : Menu)-> Unit)?){
        this.onActionMenuCreated = onActionMenuCreated
    }

    fun setOnSelectionChanged(onSelectionChanged : ((isSelected : Boolean)-> Unit)?){
        this.onSelectionChanged = onSelectionChanged
    }

    //multiItemSelector
    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)
            onSelectionChanged?.invoke(false)
            actionMode.selectedItemsCount -=1
        } else {
            selectedItems.put(position, true)
            actionMode.selectedItemsCount +=1
            onSelectionChanged?.invoke(true)
        }
        notifyItemChanged(position)
    }

    fun selectAll(){
        listItems.forEachIndexed { index, m ->
            if(m is Selectable)
                if(m.isMultiSelect())
                    selectedItems.put(index , true)
        }
        actionMode.selectedItemsCount=selectedItems.size()
        notifyDataSetChanged()
    }

    fun getItemSelectionAt(position: Int) = selectedItems.get(position , false)

    fun getSelectedItems() : MutableList<M>{
        val selectedItemsList = mutableListOf<M>()
        selectedItems.forEach { key, value ->
            if(value){
                selectedItemsList.add(listItems[key])
            }
        }
        return selectedItemsList
    }

    fun clearSelections(){
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun startSelection(position: Int){
        actionMode.startActionMode(activity,menuResId)
        actionMode.onActionMenuCreated = onActionMenuCreated
        actionMode.onActionItemClickListener = onActionItemClicked
        actionMode.onActionModeFinishedListener = ::finishActionMode
        toggleSelection(position)
    }

    private fun finishActionMode() {
        if (!actionMode.isActive) return
        actionMode.finishActionMode()
        clearSelections()

    }





}



