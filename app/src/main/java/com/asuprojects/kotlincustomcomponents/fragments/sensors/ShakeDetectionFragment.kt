package com.asuprojects.kotlincustomcomponents.fragments.sensors


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.shakedetect.ShakeDetector
import kotlinx.android.synthetic.main.fragment_shake_detection.*
import java.util.*

class ShakeDetectionFragment : Fragment(), ShakeDetector.OnShakeListener {

    private val random = Random()

    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mShakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Shakedetector inicialization
        mSensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shake_detection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = generateRandomColor()
        val hexaColor = Integer.toHexString(color).substring(2)
        val value = "#$hexaColor"
        text_sensor_color_code.text = value
        container_color_sensor.setBackgroundColor(color)

        btn_goto_shake_service.setOnClickListener {
            startActivity(Intent(requireActivity(), ShakeServiceActivity::class.java))
        }

    }

    private fun generateRandomColor(): Int {
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }

    override fun onShake(count: Int) {
        val color = generateRandomColor()
        val hexaColor = Integer.toHexString(color).substring(2)
        val value = "#$hexaColor"
        text_sensor_color_code.text = value
        container_color_sensor.setBackgroundColor(color)
    }

    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector)
        super.onPause()
    }

}
