package com.example.bidivideoviewer

import android.support.v7.widget.CardView
import android.widget.EditText
import android.widget.Switch
import android.widget.VideoView

data class StreamView(
    var cardView: CardView,
    var videoView: VideoView,
    var ipEditText: EditText,
    var portEditText: EditText,
    var connectSwitch: Switch
)