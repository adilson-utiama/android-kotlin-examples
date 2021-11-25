package com.asuprojects.kotlincustomcomponents.fragments.httprequests.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_ok_http_example.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder
import java.text.NumberFormat

class OkHttpExampleActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var isRequest = false
    private lateinit var requestViewModel: RequestViewModel

    private var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http_example)

        supportActionBar?.apply {
            this.title = "OkHttp Request"
            this.setDisplayHomeAsUpEnabled(true)
        }

        requestViewModel = ViewModelProvider(this).get(RequestViewModel::class.java)

        val requestUrl = "https://servicodados.ibge.gov.br/api/v1/projecoes/populacao"
        /**
         * {
            "localidade": "BR",
            "horario": "15/06/2020 23:34:36",
            "projecao": {
                "populacao": 211654344,
                "periodoMedio": {
                    "incrementoPopulacional": 19971,
                    "nascimento": 12000,
                    "obito": 45000
                }
              }
            }
         */

        progress_okhttp_request
        text_okhttp_request_result

        btn_okhttp_request_data.setOnClickListener {

            if(!isRequest){
                text_okhttp_request_result.text = ""
                isRequest = true
                progress_okhttp_request.visibility = VISIBLE

                val client = OkHttpClient()
                val builder = Request.Builder()
                builder.url(requestUrl)
                val request = builder.build()

                requestViewModel.viewModelScope.launch(Dispatchers.IO) {
                    try{
                        val response = client.newCall(request).execute()
                        result = response.body?.string() ?: ""
                        success()
                        Log.i("TESTE", "Response: $result")
                        isRequest = false

                    }catch (e: IOException){
                        ToastUtil.msg(this@OkHttpExampleActivity, "Falha ao fazer o Request...")
                        isRequest = false
                        error()
                    }
                }


            }

        }
    }

    private fun error(){
        requestViewModel.viewModelScope.launch {
            progress_okhttp_request.visibility = GONE
        }
    }
    /**
     * {
    "localidade": "BR",
    "horario": "15/06/2020 23:34:36",
    "projecao": {
        "populacao": 211654344,
        "periodoMedio": {
            "incrementoPopulacional": 19971,
            "nascimento": 12000,
            "obito": 45000
        }
    }
    }
     */
    private fun success() {
        requestViewModel.viewModelScope.launch {
            val numberFormat = NumberFormat.getInstance()
            val builder = StringBuilder()
            val jsonObject = JSONObject(result)
            builder.append("Localidade: ").append(jsonObject.get("localidade")).append("\n")
            builder.append("Data: ").append(jsonObject.get("horario")).append("\n")
            builder.append("População: ").append(
                numberFormat.format(
                    jsonObject.getJSONObject("projecao").get("populacao").toString().toInt()
                )
            ).append("\n")
            builder.append("Incremento Populacional: ")
                .append(
                    numberFormat.format(
                        jsonObject.getJSONObject("projecao")
                            .getJSONObject("periodoMedio")
                            .get("incrementoPopulacional").toString().toInt()
                    )
                ).append("\n")
            builder.append("Nascimentos: ")
                .append(
                    numberFormat.format(
                        jsonObject.getJSONObject("projecao")
                            .getJSONObject("periodoMedio")
                            .get("nascimento").toString().toInt()
                    )
                ).append("\n")
            builder.append("Obitos: ")
                .append(
                    numberFormat.format(
                        jsonObject.getJSONObject("projecao")
                            .getJSONObject("periodoMedio")
                            .get("obito").toString().toInt()
                    )
                ).append("\n")

            text_okhttp_request_result.text = builder.toString()
            progress_okhttp_request.visibility = GONE
        }

    }

    class RequestViewModel: ViewModel()
}