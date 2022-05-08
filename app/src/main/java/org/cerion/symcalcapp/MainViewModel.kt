package org.cerion.symcalcapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.cerion.symcalc.expression.ErrorExpr
import org.cerion.symcalc.expression.Expr
import org.cerion.symcalc.number.Integer
import org.cerion.symcalc.number.RealBigDec

private const val EXPR_PLACEHOLDER = "<EXPR>"

class MainViewModel(initialDisplay: String = "") : ViewModel() {
    private val _input = MutableLiveData(initialDisplay)

    val display = Transformations.map(_input) {
        if (it.startsWith(EXPR_PLACEHOLDER)) {
            val remaining = it.replace(EXPR_PLACEHOLDER, "")
            exprToString(lastExpr!!) + remaining
        }
        else
            it
    }

    private val _preview = MutableLiveData("")
    val preview: LiveData<String> = _preview

    private var lastExpr: Expr? = null

    init {
        if (initialDisplay.isNotEmpty())
            _preview.value = Expr.parse(initialDisplay).eval().toString()
    }

    fun clear() {
        _input.value = ""
        _preview.value = ""
    }

    // TODO auto closing bracket "(3+1 = 4"

    fun onKey(key: Key) {

        when(key) {
            Key.DEL -> {
                if (preview.value!!.isEmpty())
                    _input.value = ""
                if (_input.value!!.isNotEmpty())
                    _input.value = _input.value!!.substring(0, _input.value!!.length - 1)
            }
            Key.CLEAR -> {
                _input.value = ""
                _preview.value = ""
            }
            Key.EVAL -> {
                // TODO fix precision 8.05 - 5
                val result = eval()

                if (result.isError)
                    _preview.value = result.toString()
                else {
                    lastExpr = result
                    _input.value = EXPR_PLACEHOLDER
                    _preview.value = ""
                }
            }
            Key.NOOP -> {
                // Nothing
            }
            else -> {
                _input.value = _input.value!! + key.inputValue()
                println("updated = " + _input.value)
            }
        }

        if (key != Key.EVAL) {
            val inputEval = eval()
            if (!inputEval.isError) {
                val previewStr = exprToString(inputEval)
                if (previewStr != _input.value)
                    _preview.value = exprToString(inputEval)
            }
        }
    }

    fun directInput(input: String) {
        onKey(Key.CLEAR)
        _input.value = _input.value!! + input
        onKey(Key.NOOP)
    }

    private fun exprToString(expr: Expr): String {
        val e = when(expr) {
            is ErrorExpr -> expr
            is Integer -> expr
            else -> expr.eval(12)
        }

        return if (e is RealBigDec)
            e.toString().split("`")[0]
        else
            e.toString()
    }

    private fun eval(): Expr {
        val input = _input.value!!

        val inputExpr = if (input.startsWith(EXPR_PLACEHOLDER)) {
            val remaining = input.replace(EXPR_PLACEHOLDER, "")
            Expr.parse(lastExpr!!, remaining)
        }
        else {
            Expr.parse(input)
        }

        return inputExpr.eval()
    }
}