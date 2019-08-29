package com.example.bidivideoviewer

import android.net.Uri
import android.widget.CompoundButton
import android.widget.MediaController

class StreamViewSwitchListener(private val streamView: StreamView): CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            //TODO inject Uri into the constructor. This will remove hard-coded string format.
            val streamSource = "http://${streamView.ipEditText.text}:${streamView.portEditText.text}/stream.ts"
            val streamSourceUri = Uri.parse(streamSource)

            streamView.videoView.setMediaController(MediaController(streamView.cardView.context))
            streamView.videoView.setVideoURI(streamSourceUri)
            streamView.videoView.requestFocus()
            streamView.videoView.setOnPreparedListener {
                streamView.videoView.start()
            }
        } else {
            streamView.videoView.stopPlayback()
        }
    }
}