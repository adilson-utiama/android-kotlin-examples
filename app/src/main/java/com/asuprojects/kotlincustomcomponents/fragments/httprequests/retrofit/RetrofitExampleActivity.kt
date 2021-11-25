package com.asuprojects.kotlincustomcomponents.fragments.httprequests.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_retrofit_example.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitExampleActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_retrofit_example)

        supportActionBar?.apply {
            this.title = "Retrofit Request"
            this.setDisplayHomeAsUpEnabled(true)
        }

        btn_request_cotacao.setOnClickListener {

            text_request_cotacao_result.text = ""
            progress_request_cotacao.visibility = VISIBLE

            val requestCall = RetrofitConfig().getMoneyExchangeService().requestData("json", "1abc188b")

            requestCall.enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ToastUtil.msg(this@RetrofitExampleActivity, "Falha ao realizar o request: ${t.message}")
                    Log.i("TESTE", "Error: ${t.message}")
                    progress_request_cotacao.visibility = INVISIBLE
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    //text_request_cotacao_result.text = response.body().toString()

                    response.body()?.apply {
                        showResult(this.string())
                    }

                    progress_request_cotacao.visibility = INVISIBLE
                    Log.i("TESTE", "Data: ${response.body()?.string()}")
                }
            })
        }


    }

    private fun showResult(result: String) {
        try {
            val builder = StringBuilder()

            val jsonObject = JSONObject(result)
            val currencies = jsonObject.getJSONObject("results").getJSONObject("currencies")

            builder.append("BRL(Real): ").append("1.00").append("\n")
            builder.append("USD(Dolar): ").append(currencies.getJSONObject("USD").get("buy")).append("\n")
            builder.append("EUR(Euro): ").append(currencies.getJSONObject("EUR").get("buy")).append("\n")
            builder.append("ARS(Peso Argentino): ").append(currencies.getJSONObject("ARS").get("buy")).append("\n")

            text_request_cotacao_result.text = builder.toString()
        }catch(e: JSONException){
            Log.i("TESTE", "Erro: ${e.message}")
            text_request_cotacao_result.text = result
            ToastUtil.msg(this@RetrofitExampleActivity, "Erro ao tratar a resposta da requisicao.")
        }
    }
}