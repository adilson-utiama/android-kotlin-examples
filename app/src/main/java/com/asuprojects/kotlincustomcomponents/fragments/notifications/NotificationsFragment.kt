package com.asuprojects.kotlincustomcomponents.fragments.notifications


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.databinding.FragmentNotificationsBinding
import com.asuprojects.kotlincustomcomponents.fragments.notifications.model.NotificacaoMensagem
import com.asuprojects.kotlincustomcomponents.helpers.NotificationHelper
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.text.DateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private var _bind: FragmentNotificationsBinding? = null
    private val bind get() = _bind!!

    private val random = Random()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bind = FragmentNotificationsBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroy() {
        _bind = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.btnSimpleNotification.setOnClickListener {
            NotificationsUtil.defaultNotification(requireActivity(), "Simple Notification", "Hello Developers!, Click to Dismiss")
        }

        bind.btnInboxstyleNotification.setOnClickListener {
            NotificationsUtil.inboxStyleNotification(requireActivity(), "Inbbox Style Notification", "Message Received")
        }

        bind.btnLargetextNotification.setOnClickListener {
            NotificationsUtil.largeTextNotification(requireActivity(), "Large Text Notification",
                "Welcome to Android Studio Tutorial, it provides a tutorials related to " +
                        "all Android Programming. It helps enhance your knowledge in Android")
        }

        bind.btnImageNotification.setOnClickListener {
            NotificationsUtil.imageNotification(requireActivity(), "Image Notification")
        }

        bind.btnNotExpansivel.setOnClickListener {
            val titulo = "Notificação Expansivel ${getRandomNumber()}"
            val conteudo = "Notificação Expansivel com ID randomizado, expansivel, ideal para com conteudo com texto grande, para saber mais sobre outros estilos" +
                    " de notificacao grande, incluindo como adicionar uma imagem e controles de reproducao de midia, consulte a documentacao oficial."
            NotificationHelper.notifyLargeContentRandomId(requireActivity(), titulo, conteudo)
        }

        bind.notBtnNotificacaoIntent.setOnClickListener {
            val frm = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            val titulo = "Notificação com PendingIntent"
            val conteudo = "Notificação com Intent em ${frm.format(Calendar.getInstance().time)}, ao clicar direciona para uma tela do app."
            NotificationHelper.notifyLargeContentIntentRandomId(requireActivity(), titulo, conteudo, UUID.randomUUID().toString())
        }

        bind.notBtnNotificacaoGrupo.setOnClickListener {
            //NotificationHelper.notifyGroupedContentTest(requireActivity())
            NotificationHelper.notifyGroupedContent(
                requireActivity(),
                listOf(
                    NotificacaoMensagem("Titulo 1", "Mensagem 1", UUID.randomUUID().toString()),
                    NotificacaoMensagem("Titulo 2", "Mensagem 2", UUID.randomUUID().toString()),
                    NotificacaoMensagem("Titulo 3", "Mensagem 3", UUID.randomUUID().toString()),
                    NotificacaoMensagem("Titulo 4", "Mensagem 4", UUID.randomUUID().toString())
                )
            )
        }
    }
    private fun getRandomNumber(): Int {
        return random.nextInt(1000)
    }


}
