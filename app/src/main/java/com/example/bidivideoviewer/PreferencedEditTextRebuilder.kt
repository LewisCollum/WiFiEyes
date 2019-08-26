package com.example.bidivideoviewer

import android.widget.EditText
import android.content.SharedPreferences

class PreferencedEditTextRebuilder {
	companion object {
		var preferences: SharedPreferences? = null 
		
		fun rebuildFromPreferenceName(rebuildable: EditText, name: String): EditText {
			setTextFromPreferenceName(rebuildable, name)
			setPreferenceUpdateListeners(rebuildable, name)
			return rebuildable
		}

		private fun setTextFromPreferenceName(editText: EditText, name: String) {
			editText.setText(preferences!!.getString(name, ""))
		}
		
		private fun	setPreferenceUpdateListeners(editText: EditText, name: String) {
			editText.setOnKeyListener { _, _, _ ->
				preferences!!.edit().putString(name, editText.text.toString()).apply()
				val hasEventBeenHandled = false
				hasEventBeenHandled
			}
		}
	}
}
