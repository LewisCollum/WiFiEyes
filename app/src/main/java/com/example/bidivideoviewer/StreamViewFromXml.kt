package com.example.bidivideoviewer

import android.view.View

class StreamViewFromXml {
    companion object {
        fun makeStreamViewFromView(view: View): StreamView {
            return StreamView(
                cardView =  view.findViewById(R.id.stream_card),
                videoView = view.findViewById(R.id.stream_video),
                ipEditText = view.findViewById(R.id.ip),
                portEditText = view.findViewById(R.id.port),
                connectSwitch = view.findViewById(R.id.connect_switch),
                removeButton = view.findViewById(R.id.remove)
            )
        }
    }
}