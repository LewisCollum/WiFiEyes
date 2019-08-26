package com.example.bidivideoviewer

import android.content.pm.PackageManager

class CameraPermissionRequestHandler {
    companion object {
        private var onPermissionGrantedListener: () -> Unit = {}
        var isPermissionGranted: Boolean = false
            private set
        
        fun setOnPermissionGrantedListener(listener: () -> Unit) {
            onPermissionGrantedListener = listener
        }
                
        fun handleWithGrantResults(grantResults: IntArray) {
            val doGrantResultsExceedLength: Boolean = grantResults.size != 1
            val isPermissionDenied: Boolean = grantResults[0] != PackageManager.PERMISSION_GRANTED
            
            if (doGrantResultsExceedLength || isPermissionDenied) {
                handleCameraPermissionDenied()
            } else {
                handleCameraPermissionGranted()
            }
        }
        
        private fun handleCameraPermissionDenied() {
            //ErrorDialog.newInstance(getString(R.string.request_permission)).show(getChildFragmentManager(), FRAGMENT_DIALOG)
        }

        private fun handleCameraPermissionGranted() {
            onPermissionGrantedListener()
            isPermissionGranted = true
        }
    }
}

