package com.example.bidivideoviewer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class StreamViewAdapter: RecyclerView.Adapter<StreamViewAdapter.ViewHolder>() {
    private var streamViewCount: Int = 0

    fun addStreamView() {
        ++streamViewCount
        notifyItemInserted(streamViewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.stream_view_card, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val streamView = StreamView(
            view.findViewById(R.id.stream_card),
            view.findViewById(R.id.stream_video),
            view.findViewById(R.id.ip),
            view.findViewById(R.id.port),
            view.findViewById(R.id.connect_switch)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val streamView = holder.streamView
        streamView.ipEditText = PreferencedEditText.fromPreferenceName(streamView.ipEditText, "ip_$position")
        streamView.portEditText = PreferencedEditText.fromPreferenceName(streamView.portEditText, "port_$position")

        val streamViewSwitchListener = StreamViewSwitchListener(streamView)
        streamView.connectSwitch.setOnCheckedChangeListener(streamViewSwitchListener)
    }

    override fun getItemCount() = streamViewCount
}