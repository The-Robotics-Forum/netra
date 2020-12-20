package com.vitpunerobotics.netra.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Switch
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import com.vitpunerobotics.netra.*
import java.io.IOException

/*
* Copyright (C) The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */
class FaceTrackerActivity : BluetoothActivity() {

    private lateinit var mPreview: CameraSourcePreview
    private lateinit var mGraphicOverlay: GraphicOverlay
    private lateinit var connectbt: Switch
    private var mCameraSource: CameraSource? = null
    //==============================================================================================
    // Activity Methods
    //==============================================================================================
    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val b = Intent(applicationContext, HomeActivity::class.java)
        startActivity(b)
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_face_tracker)
        var display = windowManager.defaultDisplay
        size = Point()
        display!!.getSize(size)
        mPreview = findViewById(R.id.preview)
        mGraphicOverlay = findViewById(R.id.faceOverlay)
        connectbt = findViewById(R.id.connection)
        connectbt.setOnClickListener(View.OnClickListener {
            if (send == 0) {
                val BTconnect = Intent(applicationContext, BluetoothActivity::class.java)
                if (global_variables.bT == 0) {
                    startActivity(BTconnect)
                    global_variables.bT = 1
                }
                send = 1
            } else {
                send = 0
            }
        })


        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
        } else {
            requestCameraPermission()
        }
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private fun requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission")
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }
        val thisActivity: Activity = this
        val listener = View.OnClickListener {
            ActivityCompat.requestPermissions(thisActivity, permissions,
                    RC_HANDLE_CAMERA_PERM)
        }
        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show()
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private fun createCameraSource() {
        val context = applicationContext
        val detector = FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build()
        detector.setProcessor(
                MultiProcessor.Builder(GraphicFaceTrackerFactory())
                        .build())
        if (!detector.isOperational) {
            AlertDialog.Builder(this)
                    .setMessage("Face detector dependencies are not yet available.")
                    .show()
            Log.w(TAG, "Face detector dependencies are not yet available.")
            return
        }
        mCameraSource = CameraSource.Builder(context, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1800, 750)
                .build()
    }

    /**
     * Restarts the camera.
     */
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /**
     * Stops the camera.
     */
    override fun onPause() {
        super.onPause()
        mPreview.stop()
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mCameraSource != null) {
            mCameraSource!!.release()
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on [.requestPermissions].
     *
     *
     * **Note:** It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     *
     *
     * @param requestCode  The request code passed in [.requestPermissions].
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either [PackageManager.PERMISSION_GRANTED]
     * or [PackageManager.PERMISSION_DENIED]. Never null.
     * @see .requestPermissions
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source")
            // we have permission, so create the camerasource
            createCameraSource()
            return
        }
        Log.e(TAG, "Permission not granted: results len = " + grantResults.size +
                " Result code = " + if (grantResults.size > 0) grantResults[0] else "(empty)")
        val listener = DialogInterface.OnClickListener { dialog, id -> finish() }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show()
    }
    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================
    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {

        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }
        }
    }
    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================
    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private inner class GraphicFaceTrackerFactory : MultiProcessor.Factory<Face> {
        override fun create(face: Face): GraphicFaceTracker<Face> {
            return GraphicFaceTracker(mGraphicOverlay, this@FaceTrackerActivity)
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private inner class GraphicFaceTracker<T>(private val mOverlay: GraphicOverlay?, context: Context?) : Tracker<Face?>() {
        private val mFaceGraphic: FaceGraphic

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        override fun onNewItem(faceId: Int, item: Face?) {
            mFaceGraphic.setId(faceId)
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        override fun onUpdate(detectionResults: Detections<Face?>, face: Face?) {
            mOverlay!!.add(mFaceGraphic)
            mFaceGraphic.updateFace(face)
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        override fun onMissing(detectionResults: Detections<Face?>) {
            mOverlay!!.remove(mFaceGraphic)
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        override fun onDone() {
            mOverlay!!.remove(mFaceGraphic)
        }

        init {
            mFaceGraphic = FaceGraphic(mOverlay, context!!)
        }
    }

    companion object {
        private const val TAG = "FaceTracker"
        private const val RC_HANDLE_GMS = 9001

        // permission request codes need to be < 256
        private const val RC_HANDLE_CAMERA_PERM = 2
        var size: Point? = null
        var send = 0
    }
}