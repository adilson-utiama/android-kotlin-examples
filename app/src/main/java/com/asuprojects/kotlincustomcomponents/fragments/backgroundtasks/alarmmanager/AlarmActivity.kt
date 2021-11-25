package com.asuprojects.kotlincustomcomponents.fragments.backgroundtasks.alarmmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.core.os.HandlerCompat
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.bottomsheet.custom.CustomBSDialogInfo
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_alarm.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private val defaultMsg = "nenhuma mensagem para o alarme"
    private var dataAtual = Calendar.getInstance()

    private var desbloquearBotao = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        supportActionBar?.apply {
            this.title = "Alarm Manager Uso"
            this.setDisplayHomeAsUpEnabled(true)
        }

        alarm_time_picker.setIs24HourView(true)
        alarm_time_picker.setOnTimeChangedListener { view, hourOfDay, minute ->
            val time = "Horario Selecionado: $hourOfDay:$minute"
            dataAtual.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dataAtual.set(Calendar.MINUTE, minute)
        }

        btn_setar_alarme.setOnClickListener {

            if(desbloquearBotao) {

                desbloquearBotao = false

                var msg = text_alarme_msg.text.toString()
                if(msg.isEmpty()){
                    msg = defaultMsg
                }

                dataAtual.set(Calendar.SECOND, 0)
                dataAtual.set(Calendar.MILLISECOND, 0)
                val alarmeHorario = dataAtual.timeInMillis

                AlarmHelper.setarAlarmParaNotificacao(this@AlarmActivity, msg, alarmeHorario)

                val bsDialogInfo = CustomBSDialogInfo()
                val format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
                bsDialogInfo.setMsg("Alarme Setado para as: \n${format.format(dataAtual.time)}")
                bsDialogInfo.show(supportFragmentManager, bsDialogInfo.tag)

                text_alarme_msg.text?.clear()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val dateInstance = Calendar.getInstance()
                    val hour = dateInstance.get(Calendar.HOUR_OF_DAY)
                    val minute = dateInstance.get(Calendar.MINUTE)
                    alarm_time_picker.hour = hour
                    alarm_time_picker.minute = minute
                }

            }else{
                ToastUtil.msg(this@AlarmActivity, "Botao Bloqueado por 7 segundos")
            }

            GlobalScope.launch {
                delay(7000)
                desbloquearBotao = true
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
