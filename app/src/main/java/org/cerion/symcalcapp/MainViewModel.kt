package org.cerion.symcalcapp

import androidx.compose.runtime.produceState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cerion.symcalc.expression.Expr

class MainViewModel(initialDisplay: String = "") : ViewModel() {
    private val _display = MutableLiveData(initialDisplay)
    val display: LiveData<String> = _display

    private val _preview = MutableLiveData("")
    val preview: LiveData<String> = _preview

    init {
        if (initialDisplay.isNotEmpty())
            _preview.value = Expr.parse(initialDisplay).eval().toString()
    }

    fun clear() {
        _display.value = ""
        _preview.value = ""
    }

    // TODO auto closing bracket "(3+1 = 4"

    fun onKey(key: Key) {

        when(key) {
            Key.DEL -> {
                if (preview.value!!.isEmpty())
                    _display.value = ""
                if (display.value!!.isNotEmpty())
                    _display.value = display.value!!.substring(0, display.value!!.length - 1)
            }
            Key.EVAL -> {
                // TODO fix precision 8.05 - 5
                val inputExpr = Expr.parse(display.value!!)
                val result = inputExpr.eval()

                if (result.isError)
                    _preview.value = result.toString()
                else {
                    _display.value = result.toString()
                    _preview.value = ""
                }
            }
            else -> {
                _display.value = display.value!! + key.inputValue()
            }
        }

        if (key != Key.EVAL) {
            val inputEval = Expr.parse(display.value!!).eval()
            if (!inputEval.isError && inputEval.toString() != display.value!!)
                _preview.value = inputEval.toString()
        }
    }
}