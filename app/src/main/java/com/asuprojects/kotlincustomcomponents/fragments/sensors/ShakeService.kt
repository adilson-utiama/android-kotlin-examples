package com.asuprojects.kotlincustomcomponents.fragments.sensors

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import java.util.*

class ShakeService : Service(), SensorEventListener  {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private var mAccel: Float = 0F
    private var mAccelCurrent: Float = 0F
    private var mAccelLast: Float = 0F

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI, Handler())

        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        mAccelLast = mAccelCurrent
        mAccelCurrent = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val delta = mAccelCurrent - mAccelLast
        mAccel = mAccel * 0.9f + delta // perform low-cut filter

        if (mAccel > 11) {
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            ShakeServiceActivity.tvShakeService.text = "Service detects the Shake Action!! Color is also changed..!!!"
            ShakeServiceActivity.tvShakeService.setTextColor(color)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}