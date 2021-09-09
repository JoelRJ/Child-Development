package com.example.childdevelopment.overview

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.network.AgesOption
import com.example.childdevelopment.network.MilestonesOption
//https://spin.atomicobject.com/2019/06/08/kotlin-recyclerview-data-binding/

val <T : Any > T.classNameKotlin: String?
    get() {
        return javaClass.kotlin.simpleName
    }

@BindingAdapter("agesData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     listData: List<AgesOption>?) {
    val adapter = recyclerView.adapter as? AgesAdapter
    adapter?.submitList(listData)
}

