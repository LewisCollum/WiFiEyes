package com.example.bidivideoviewer

import android.support.v7.widget.CardView
import android.widget.*

data class StreamView(
    var cardView: CardView,
    var videoView: VideoView,
    var ipEditText: EditText,
    var portEditText: EditText,
    var connectSwitch: Switch,
    var removeButton: ImageButton
)