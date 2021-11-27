package com.asuprojects.kotlincustomcomponents.fragments.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asuprojects.kotlincustomcomponents.databinding.ActivityNotificationAfterBinding
import com.asuprojects.kotlincustomcomponents.helpers.AppConstants.Companion.NOTIFICATION_EXTRA_UUID

class NotificationAfterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationAfterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationAfterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.apply {

            if(this.hasExtra(NOTIFICATION_EXTRA_UUID)) {
                this.getStringExtra(NOTIFICATION_EXTRA_UUID)?.let {
                    binding.uuid.text = it
                }
            }else{
                binding.uuid.text = "UUID não Recebido"
            }

        }

        binding.btnClose.setOnClickListener {
            onBackPressed()
        }

    }

}
