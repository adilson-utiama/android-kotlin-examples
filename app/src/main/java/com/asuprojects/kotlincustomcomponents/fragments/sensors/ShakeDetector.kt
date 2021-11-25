package com.asuprojects.shakedetect

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class ShakeDetector(val mListener: OnShakeListener?) : SensorEventListener {

    private val SHAKE_THRESHOLD_GRAVITY: Float = 2.7F
    private val SHAKE_SLOP_TIME_MS = 500
    private val SHAKE_COUNT_RESET_TIME_MS = 3000

    private var mShakeTimeStamp: Long = 0
    private var mShakeCounts: Int = 0

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event?.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // gForce will be close to 1 when there is no movement.
        val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble())

        if(gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            // ignore shake events too close to each other (500ms)
            if(mShakeTimeStamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }
            // reset the shake count after 3 seconds of no shakes
            if(mShakeTimeStamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                mShakeCounts = 0
            }

            mShakeTimeStamp = now
            mShakeCounts++

            mListener?.onShake(mShakeCounts)

        }
    }

    interface OnShakeListener {
        fun onShake(count: Int)
    }
}