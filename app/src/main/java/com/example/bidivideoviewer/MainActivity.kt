package com.example.bidivideoviewer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Handler

import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CameraCaptureSession

import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.VideoView
import android.widget.MediaController
import android.widget.Switch
import android.view.SurfaceView
import android.Manifest.permission
import android.content.pm.PackageManager

import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Surface
import android.media.ImageReader
import android.media.Image
import android.view.SurfaceHolder

class MainActivity : Activity() {
    private lateinit var ip: EditText
    private lateinit var port: EditText
    private lateinit var connectSwitch: Switch
    private lateinit var streamView: VideoView
	
	private lateinit var cameraView: SurfaceView
	private lateinit var cameraManager: CameraManager
	private lateinit var camera: CameraDevice
	private lateinit var cameraCaptureRequestBuilder: CaptureRequest.Builder 
	private lateinit var cameraId: String
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		PreferencedEditTextRebuilder.preferences = App.preferences
		
		setViewObjectsFromXml()

		startStreamViewWhenConnectSwitchOn()
		requestCameraPermission()
		
		//TODO implement startCameraStreamWhenStreamSwitchOn()
    }

	override fun onResume() {
		super.onResume()
		if (CameraPermissionRequestHandler.isPermissionGranted) openCamera()
	}

	private fun setViewObjectsFromXml() {
		ip = PreferencedEditTextRebuilder.rebuildFromPreferenceName(findViewById(R.id.ip), "ip")
        port = PreferencedEditTextRebuilder.rebuildFromPreferenceName(findViewById(R.id.port), "port")
		connectSwitch = findViewById(R.id.connectSwitch)
		streamView = findViewById(R.id.streamView)
		cameraView = findViewById(R.id.cameraView)
	}

	//TODO extract SwitchableStreamView class. StreamView class, Switchable decorator.
	private fun startStreamViewWhenConnectSwitchOn() {
		connectSwitch.setOnCheckedChangeListener {_, isChecked ->
			if (isChecked) {
				val streamSource = "http://${ip.text}:${port.text}/stream.ts"
				val streamSourceUri = Uri.parse(streamSource)
				streamView.setMediaController(MediaController(this))
				streamView.setVideoURI(streamSourceUri)
				streamView.requestFocus()
				streamView.setOnPreparedListener {
					streamView.start()
				}
			} else {
				streamView.stopPlayback()
			}
		}
	}

	//todo extract SimpleCamera class
	private fun openCamera() {		
		Log.v("MYLOG", "Opening")
		cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
		getCameraIdFromCameraManager()
		openCameraWithId()

	}
	private fun requestCameraPermission() {
		ActivityCompat.requestPermissions(this, arrayOf(permission.CAMERA), App.arbitraryCameraRequestCode)
	}
	private fun getCameraIdFromCameraManager() {
		cameraId = cameraManager.cameraIdList[1]
	}
	private fun openCameraWithId() {
		cameraManager.openCamera(cameraId, object: CameraDevice.StateCallback() {
			override fun onOpened(cameraDevice: CameraDevice) {
				camera = cameraDevice
				//TODO generalize this function in SimpleCameraClass. Move startCamera to CameraView class.
				startCameraView()
			}
			override fun onDisconnected(cameraDevice: CameraDevice) {
				cameraDevice.close()
			}
			override fun onError(cameraDevice: CameraDevice, error: Int) {}
		}, null)
	}

	//TODO extract to CameraView class
	private fun startCameraView() {
		Log.v("MYLOG", "startCameraView")
		//var cameraSurfaceHolder = cameraView.holder
		var cameraSurface = cameraView.holder.surface
	
		cameraCaptureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
		cameraCaptureRequestBuilder.addTarget(cameraSurface)
		
		camera.createCaptureSession(mutableListOf(cameraSurface), object: CameraCaptureSession.StateCallback() {
			override fun onConfigured(session: CameraCaptureSession) {
				cameraCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
				var cameraCaptureRequest: CaptureRequest = cameraCaptureRequestBuilder.build()
				session.setRepeatingRequest(cameraCaptureRequest, null, null)
			}
			override fun onConfigureFailed(session: CameraCaptureSession) {}
		}, null)
	}


	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		if (requestCode == App.arbitraryCameraRequestCode) {
			CameraPermissionRequestHandler.handleWithGrantResults(grantResults)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
	}	
}
























		// cameraSurfaceHolder.addCallback(object: SurfaceHolder.Callback {
		// 	override fun surfaceCreated(holder: SurfaceHolder) {
		// 		Log.v("MYLOG", "surfaceCreated")
		// 		cameraCaptureRequestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
		// 		cameraCaptureRequestBuilder.addTarget(holder.surface)
				
		// 		camera.createCaptureSession(mutableListOf(holder.surface), object: CameraCaptureSession.StateCallback() {
		// 			override fun onConfigured(session: CameraCaptureSession) {
		// 				cameraCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
		// 				var cameraCaptureRequest: CaptureRequest = cameraCaptureRequestBuilder.build()
		// 				session.setRepeatingRequest(cameraCaptureRequest, null, null)
		// 			}
		// 			override fun onConfigureFailed(session: CameraCaptureSession) {}
		// 		}, null)
		// 	}
			
		// 	override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int){}
		// 	override fun surfaceDestroyed(holder: SurfaceHolder) {}
		// })
