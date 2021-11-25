package com.asuprojects.kotlincustomcomponents.fragments.inputs.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

interface OnChangeValueListener{
    fun onChangeValue(value: Double)
}

class AsuCurrencyEditText : AppCompatEditText {

    private var current: String = ""
    private var editText = this
    private var onChangeValueListener: OnChangeValueListener? = null

    //Properties
    var currency: String = ""
    var separator: String = ","
    var spacing: Boolean = false
    var delimiter: Boolean = false
    var decimals: Boolean = true

    var parsedDouble = 0.0

    fun setOnChangeValueListener(listener: OnChangeValueListener){
        this.onChangeValueListener = listener
    }

    constructor(context: Context) : super(context!!){
        init()
    }

    constructor(context: Context, attrs:  AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    fun init(){

        this.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().equals(current)){
                    editText.removeTextChangedListener(this)

                    val cleanString = s.toString()
                        .replace("[$,.]".toRegex(), "")
                        .replace(currency, "")
                        .replace("\\s+".toRegex(), "")

                    if(cleanString.isNotEmpty()){
                        try{
                            val currencyFormat: String
                            if(spacing){
                                if(delimiter){
                                    currencyFormat = currency + "."
                                }else{
                                    currencyFormat = currency + " "
                                }
                            }else{
                                if(delimiter){
                                    currencyFormat = currency + "."
                                }else{
                                    currencyFormat = currency
                                }
                            }

                            val parsed: Double
                            val parsedInt: Int
                            val formatted: String

                            if(decimals){
                                parsed = cleanString.toDouble()

                                parsedDouble = (parsed / 100)

                                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                                    .replace(NumberFormat.getCurrencyInstance().currency.symbol, currencyFormat)

                                onChangeValueListener?.onChangeValue(parsed / 100)
                            }else{
                                parsedInt = cleanString.toInt()
                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt)
                            }

                            current = formatted

                            //If decimals are turned off and Separator is set as anything other commas
                            if(separator != "," && !decimals){
                                //..replace the commas with the new separator
                                editText.setText(formatted.replace(",", separator))
                            }else{
                                //since no custom separators were set, proceed with commas separator
                                editText.setText(formatted)
                            }
                            editText.setSelection(formatted.length)
                        }catch(e: NumberFormatException){

                        }
                    }

                    editText.addTextChangedListener(this)


                }
            }

        })
    }

    fun getCleanDoubleValue(): Double{
        var value = 0.0
        if(decimals){
            try{
                val cleanString = this.text.toString()
                    .replace("[$,.]".toRegex(), "")
                    .replace(currency, "")
                    .replace("\\s+".toRegex(), "")

                val double = cleanString.toDouble() / 100
                value = double
            }catch(e: Exception){

            }

        }else{
            val cleanString = editText.text.toString()
                .replace("[$,.]", "")
                .replace(currency, "")
                .replace("\\s+".toRegex(), "")
            try{
                value = cleanString.toDouble()
            }catch(e: Exception){

            }
        }
        return value
    }

    fun getCleanIntValue(): Int{
        var value = 0
        if(decimals){
            val doubleValue = editText.text.toString()
                .replace("[$,]".toRegex(), "")
                .replace(currency,"").toDouble()
            value = doubleValue.roundToInt()
        }else{
            val cleanString = editText.text.toString()
                .replace("[$,.]".toRegex(), "")
                .replace(currency,"")
                .replace("\\s+".toRegex(), "")
            try{
                value = cleanString.toInt()
            }catch(e: NumberFormatException){

            }
        }
        return value
    }



}