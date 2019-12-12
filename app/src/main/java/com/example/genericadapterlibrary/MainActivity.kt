package com.example.genericadapterlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kot.kotadapter.adpter.KotAdapter
import com.kot.kotadapter.adpter.base.KotRecyclerAdapter
import com.kot.kotadapter.adpter.viewholders.KotViewHolder
import com.kot.kotadapter.adpter.onChange
import com.kot.kotadapter.adpter.toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.tv_body
import kotlinx.android.synthetic.main.list_item.view.tv_title

class MainActivity : AppCompatActivity() {

    var mAdapter: KotRecyclerAdapter<Any>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val list = mutableListOf<Any>().apply {
            add(Ads("Ads title 1", "Ads Body 1"))
            add(Persons("Person 1", "About person 1"))
            add(Persons("Person 2", "About person 2"))
            add(Header("Header 1"))
            add(Persons("Person 3", "About person 3"))
            add(Persons("Person 4", "About person 4"))
            add(Persons("Person 5", "About person 5"))
            add(Header("Header 2"))
            add(Ads("Ads title 1", "Ads Body 1"))
            add(Persons("Person 6", "About person 6"))
            add(Persons("Person 7", "About person 7"))
            add(Header("Header 3"))
            add(Persons("Person 8", "About person 8"))
            add(Persons("Person 1", "About person 1"))
            add(Ads("Ads title 1", "Ads Body 1"))
            add(Persons("Person 2", "About person 2"))
            add(Header("Header 1"))
            add(Persons("Person 3", "About person 3"))
            add(Persons("Person 4", "About person 4"))
            add(Persons("Person 5", "About person 5"))
            add(Header("Header 2"))
            add(Persons("Person 6", "About person 6"))
            add(Persons("Person 7", "About person 7"))
            add(Header("Header 3"))
            add(Persons("Person 8", "About person 8"))
            add(Persons("Person 1", "About person 1"))
            add(Persons("Person 2", "About person 2"))
            add(Header("Header 1"))
            add(Persons("Person 3", "About person 3"))
            add(Persons("Person 4", "About person 4"))
            add(Persons("Person 5", "About person 5"))
            add(Header("Header 2"))
            add(Persons("Person 6", "About person 6"))
            add(Persons("Person 7", "About person 7"))
            add(Header("Header 3"))
            add(Persons("Person 8", "About person 8"))
            add(Persons("Person 1", "About person 1"))
            add(Persons("Person 2", "About person 2"))
            add(Header("Header 1"))
            add(Persons("Person 3", "About person 3"))
            add(Persons("Person 4", "About person 4"))
            add(Persons("Person 5", "About person 5"))
            add(Header("Header 2"))
            add(Persons("Person 6", "About person 6"))
            add(Persons("Person 7", "About person 7"))
            add(Header("Header 3"))
            add(Persons("Person 8", "About person 8"))
        }

        /* val adapter = object : KotBaseAdapter<Any>(this,list){
             override fun getLayoutId(position: Int, obj: Any): Int {
                 return when(obj){
                     is Persons-> R.layout.list_item
                     else -> R.layout.item_header
                 }
             }
             override fun getViewHolder(view: View, viewType: Int): KotBaseViewHolder<Any> {
                 return when(viewType){
                     R.layout.list_item-> PersonViewHolder(view)
                     else -> HeaderViewHolder(view)
                 } as KotBaseViewHolder<Any>
             }
          }.setActionModeMenu(R.menu.selection_menu)
             .setOnActionItemClicked {
                 when (it.itemId) {
                     R.id.nav_all -> toast("All")
                 }
             }*/






        et_search.onChange {
            mAdapter?.search(it)
        }




        fab.setOnClickListener {
            mAdapter = KotAdapter {
                with(this@MainActivity)
                menuRes(R.menu.selection_menu)
                setOnActionMenuCreatedListener { }
                setOnActionItemClickedListener {
                    when (it.itemId) {
                        R.id.nav_all -> mAdapter?.selectAll()
                    }
                }
                setOnItemSelectionChangeListener { toast("$it") }
                setOnActionModeFinishListener { }
                addModel(
                    R.layout.list_item,
                    PersonViewHolder::class.java,
                    Persons::class.java
                )
                addModel(
                    R.layout.item_header,
                    HeaderViewHolder::class.java,
                    Header::class.java
                )
                addModel(
                    R.layout.item_ads,
                    AdsViewHolder::class.java,
                    Ads::class.java
                )
            }

            mAdapter?.setItems(list)

            rv_list.layoutManager = LinearLayoutManager(this)
            rv_list.adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class PersonViewHolder(itemView: View) : KotViewHolder<Persons>(itemView) {
        override fun bindData(element: Persons, adapter: KotRecyclerAdapter<Persons>) {
            itemView.tv_title.text = element.name
            itemView.tv_body.text = element.about
            itemView.chk.setOnClickListener {
                adapter.toggleSelection(adapterPosition)
            }
            itemView.chk.isChecked = adapter.getItemSelectionAt(adapterPosition)

            itemView.setOnLongClickListener {
                adapter.startSelection(adapterPosition)
                true
            }
        }


    }

    class HeaderViewHolder(itemView: View) : KotViewHolder<Header>(itemView) {
        override fun bindData(element: Header, adapter: KotRecyclerAdapter<Header>) {
            itemView.tv_header.text = element.title
        }

    }

    class AdsViewHolder(itemView: View) : KotViewHolder<Ads>(itemView) {
        override fun bindData(element: Ads, adapter: KotRecyclerAdapter<Ads>) {
            itemView.tv_title.text = element.title
            itemView.tv_body.text = element.body
        }


    }
}
