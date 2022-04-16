package org.cerion.symcalcapp

import androidx.compose.runtime.produceState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cerion.symcalc.expression.Expr
import org.cerion.symcalc.function.core.N
import org.cerion.symcalc.number.Integer
import org.cerion.symcalc.number.RealBigDec

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

    private val input: String
        get() {
            return display.value!!
                .replace("Log(", "Log10(")
                .replace("Ln(", "Log(")
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
            Key.CLEAR -> {
                _display.value = ""
                _preview.value = ""
            }
            Key.EVAL -> {
                // TODO fix precision 8.05 - 5
                val inputExpr = Expr.parse(input)
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
            val inputEval = Expr.parse(input).eval()
            if (!inputEval.isError && inputEval.toString() != input)
                _preview.value = inputEval.toString()
        }
    }

    // TODO make this match the eval for onKey
    fun directInput(input: String) {
        val exactEval = Expr.parse(input).eval()

        val inputEval = when(exactEval) {
            is Integer -> exactEval
            else -> Expr.parse(input).eval(12)
        }

        if (!inputEval.isError && inputEval.toString() != input) {
            if (inputEval is RealBigDec)
                _preview.value = inputEval.toString().split("`")[0]
            else
                _preview.value = inputEval.toString()
        }
    }
}