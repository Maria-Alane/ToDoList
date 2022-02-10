package com.chaveirinho.todolist.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

//adicionando locale
private val locale = Locale("pt", "BR")
//Formato para a data
fun Date.format(): String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}
//
var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }