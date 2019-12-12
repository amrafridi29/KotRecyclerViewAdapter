package com.example.genericadapterlibrary

import com.kot.kotadapter.adpter.imp.Searchable
import com.kot.kotadapter.adpter.imp.Selectable

data class Persons(var name : String , val about : String) :
    Searchable, Selectable {
    override fun isMultiSelect(): Boolean {
        return true
    }

    override fun getSearchCriteria(): String {
        return name
    }


}