package com.example.bidivideoviewer

import android.app.Activity

import android.os.Bundle
import android.view.View

class MainActivity : Activity() {
    private lateinit var addStreamViewButton: View
    private lateinit var streamViewRecycler: StreamViewRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferencedEditText.preferences = App.preferences
        
        setViewObjectsFromXml()
        addStreamViewToUiWhenStreamButtonClicked()
    }

    private fun setViewObjectsFromXml() {
        addStreamViewButton = findViewById(R.id.add_stream_button)
        streamViewRecycler = StreamViewRecycler(this, findViewById(R.id.stream_scroll))
    }

    private fun addStreamViewToUiWhenStreamButtonClicked() {
        addStreamViewButton.setOnClickListener {
            streamViewRecycler.addStreamView()
        }
    }
}