package com.example.bidivideoviewer

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class StreamViewRecycler(context: Context, recyclerView: RecyclerView) {
    private val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    private val adapter = StreamViewAdapter()

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    fun addStreamView() = adapter.addStreamView()
}