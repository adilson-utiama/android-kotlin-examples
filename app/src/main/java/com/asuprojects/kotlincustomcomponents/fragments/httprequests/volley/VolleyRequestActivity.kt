package com.asuprojects.kotlincustomcomponents.fragments.httprequests.volley

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.fragments.inputs.custom.MaskWatcher
import com.asuprojects.kotlincustomcomponents.helpers.NetworkUtils
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_volley_request.*
import org.json.JSONException
import org.json.JSONObject

class VolleyRequestActivity : AppCompatActivity(),
    Response.Listener<JSONObject>,
    Response.ErrorListener{

    private var isRequest = true

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onDestroy() {
        super.onDestroy()
        networkCallback?.apply {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                connectivityManager.unregisterNetworkCallback(this)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_request)

        supportActionBar?.apply {
            this.title = "Volley Http Request"
            this.setDisplayHomeAsUpEnabled(true)
        }

        networkCallback = NetworkUtils.addNetworkCallback(this)

        val requestQueue = Volley.newRequestQueue(this)

        val maskWatcher = MaskWatcher("#####-###")
        custom_input_cep_mask_request.addTextChangedListener(maskWatcher)

        btn_buscar_cep.setOnClickListener {
            if(isRequest) {

                isRequest = false

                progress_request.visibility = VISIBLE
                val cep = custom_input_cep_mask_request.text.toString()

                val parametro = cep.replace("-".toRegex(), "")

                Log.i("TESTE", "CEP INPUTED: $parametro")

                if(parametro.length == 8){
                    text_cep_request_response_result.text = ""
                    val url = "https://viacep.com.br/ws/$parametro/json/"
                    val jsonObjectRequest  = JsonObjectRequest(Request.Method.GET, url, null, this, this)
                    requestQueue.add(jsonObjectRequest)
                }else{
                    ToastUtil.msg(this@VolleyRequestActivity, "Parametro invalido!")
                    isRequest = true
                    progress_request.visibility = GONE
                }
            }
        }
    }

    override fun onResponse(response: JSONObject?) {
        /**
         * {
        "cep": "01001-000",
        "logradouro": "Praça da Sé",
        "complemento": "lado ímpar",
        "bairro": "Sé",
        "localidade": "São Paulo",
        "uf": "SP",
        "unidade": "",
        "ibge": "3550308",
        "gia": "1004"
        }
         */
        try{
            val builder = StringBuilder()
            response?.apply {
                builder.append("Cep: ").append(this.get("cep")).append("\n")
                    .append("Logradouro: ").append(this.get("logradouro")).append("\n")
                    .append("Complemento: ").append(this.get("complemento")).append("\n")
                    .append("Bairro: ").append(this.get("bairro")).append("\n")
                    .append("Localidade: ").append(this.get("localidade")).append("\n")
                    .append("UF: ").append(this.get("uf")).append("\n")
                    .append("Unidade: ").append(this.get("unidade")).append("\n")
                    .append("IBGE: ").append(this.get("ibge")).append("\n")
                    .append("Gia: ").append(this.get("gia")).append("\n")

                text_cep_request_response_result.text = builder.toString()

                isRequest = true
                progress_request.visibility = GONE
                custom_input_cep_mask_request.text?.clear()
            }
        }catch(e: JSONException){
            ToastUtil.msg(this@VolleyRequestActivity, "Erro ao tratar resposta: ${e.message}")
            isRequest = true
            progress_request.visibility = GONE
        }
    }

    override fun onErrorResponse(error: VolleyError?) {
        ToastUtil.msg(this@VolleyRequestActivity, "Erro ao fazer request: ${error?.message}")
    }
}