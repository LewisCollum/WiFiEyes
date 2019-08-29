package com.example.bidivideoviewer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class StreamViewAdapter: RecyclerView.Adapter<StreamViewAdapter.ViewHolder>() {
    private var streamViewCount: Int = 0
    private lateinit var streamView: StreamView

    fun addStreamView() {
        ++streamViewCount
        notifyItemInserted(streamViewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.stream_view_card, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        streamView = StreamViewFromXml.makeStreamViewFromView(holder.itemView)
        handleEditTextPreferencesByPosition(position)
        handleStreamConnection()
        handleRemovingStreamViewsByPosition(position)
    }

    private fun handleEditTextPreferencesByPosition(position: Int) {
        streamView.ipEditText = PreferencedEditText.fromPreferenceName(streamView.ipEditText, "ip_$position")
        streamView.portEditText = PreferencedEditText.fromPreferenceName(streamView.portEditText, "port_$position")
    }

    private fun handleStreamConnection() {
        val streamViewSwitchListener = StreamViewSwitchListener(streamView)
        streamView.connectSwitch.setOnCheckedChangeListener(streamViewSwitchListener)
    }

    private fun handleRemovingStreamViewsByPosition(position: Int) {
        streamView.removeButton.setOnClickListener {
            streamView.connectSwitch.isChecked = false
            removeStreamViewByPosition(position)
        }
    }

    private fun removeStreamViewByPosition(position: Int) {
        --streamViewCount
        notifyItemRemoved(position)
    }

    override fun getItemCount() = streamViewCount
}