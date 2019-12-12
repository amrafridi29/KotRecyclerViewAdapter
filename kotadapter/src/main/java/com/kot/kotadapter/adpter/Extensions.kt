package com.kot.kotadapter.adpter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.kot.kotadapter.adpter.base.KotAdapterBuilder

fun EditText.onChange(OnChange: ((s: String?)-> Unit)){
    this.addTextChangedListener(object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            OnChange.invoke(s.toString())
        }

    })
}

fun Context.toast(msg : String){
    Toast.makeText(this ,msg , Toast.LENGTH_SHORT).show()
}

fun <T : Any> Context.KotAdapter(init : KotAdapterBuilder<T>.()-> Unit) = KotAdapterBuilder<T>().apply { init() }.build()

