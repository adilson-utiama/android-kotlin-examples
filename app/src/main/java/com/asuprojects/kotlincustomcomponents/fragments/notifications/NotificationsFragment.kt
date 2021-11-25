package com.asuprojects.kotlincustomcomponents.fragments.notifications


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_simple_notification.setOnClickListener {
            NotificationsUtil.defaultNotification(requireActivity(), "Simple Notification", "Hello Developers!, Click to Dismiss")
        }

        btn_inboxstyle_notification.setOnClickListener {
            NotificationsUtil.inboxStyleNotification(requireActivity(), "Inbbox Style Notification", "Message Received")
        }

        btn_largetext_notification.setOnClickListener {
            NotificationsUtil.largeTextNotification(requireActivity(), "Large Text Notification",
                "Welcome to Android Studio Tutorial, it provides a tutorials related to " +
                        "all Android Programming. It helps enhance your knowledge in Android")
        }

        btn_image_notification.setOnClickListener {
            NotificationsUtil.imageNotification(requireActivity(), "Image Notification")
        }
    }

}
