package com.asuprojects.kotlincustomcomponents.fragments.async.asynctask

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import com.asuprojects.kotlincustomcomponents.R
import com.asuprojects.kotlincustomcomponents.helpers.ToastUtil
import kotlinx.android.synthetic.main.activity_async_task.*

class AsyncTaskActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_async_task)

        supportActionBar?.apply {
            this.title = "Async Task"
            this.setDisplayHomeAsUpEnabled(true)
        }

        btn_start_count.setOnClickListener {
            async_count_result.text = "0"
            val seconds = async_et_count_seconds.text.toString()
            if(seconds.isNotEmpty()){
                DoSomethingAsyncTask(
                    this@AsyncTaskActivity,
                    async_count_result
                ).execute(seconds.toInt())
            }else{
                ToastUtil.msg(this@AsyncTaskActivity, "Informe os segundos")
                async_et_count_seconds.requestFocus()
            }
        }
    }

    /**
     * AsyncTask
     * This class was deprecated in API level 30.
     * Use the standard java.util.concurrent or Kotlin concurrency utilities instead.
     */
    private class DoSomethingAsyncTask(
        val context: Context,
        val textView: AppCompatTextView
    ) : AsyncTask<Int, Int, Void>(){

        override fun doInBackground(vararg params: Int?): Void? {

            val seconds = params[0]

            for(sec in 1..seconds!!){
                Thread.sleep(1000)

                publishProgress(sec)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            ToastUtil.msg(context, "Count Finished!")
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            textView.text = values[0]!!.toString()
        }


    }
}