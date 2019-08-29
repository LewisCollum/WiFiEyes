package com.example.bidivideoviewer

import android.widget.EditText
import android.content.SharedPreferences

class PreferencedEditText {
	companion object {
		var preferences: SharedPreferences? = null 
		
		fun fromPreferenceName(editText: EditText, name: String): EditText {
			setTextFromPreferenceName(editText, name)
			setPreferenceUpdateListeners(editText, name)
			return editText
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
