package org.cerion.symcalcapp

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.cerion.symcalc.expression.ErrorExpr
import org.cerion.symcalc.expression.Expr
import org.cerion.symcalc.number.Integer
import org.cerion.symcalc.number.RealBigDec

private const val EXPR_PLACEHOLDER = "<EXPR>"

class MainViewModel(initialDisplay: String = "") : ViewModel() {
    private var input by mutableStateOf(initialDisplay)
    private var lastExpr: Expr? = null

    val display by derivedStateOf {
        if (input.startsWith(EXPR_PLACEHOLDER)) {
            val remaining = input.replace(EXPR_PLACEHOLDER, "")
            exprToString(lastExpr!!) + remaining
        }
        else
            input
    }

    var preview by mutableStateOf("")
        private set

    init {
        if (initialDisplay.isNotEmpty())
            preview = Expr.parse(initialDisplay).eval().toString()
    }

    fun clear() {
        input = ""
        preview = ""
    }

    // TODO auto closing bracket "(3+1 = 4"

    fun onKey(key: Key) {

        when(key) {
            Key.DEL -> {
                if (preview.isEmpty())
                    input = ""
                if (input.isNotEmpty())
                    input = input.substring(0, input.length - 1)
            }
            Key.CLEAR -> {
                input = ""
                preview = ""
            }
            Key.EVAL -> {
                // TODO fix precision 8.05 - 5
                val result = eval()

                if (result.isError)
                    preview = result.toString()
                else {
                    lastExpr = result
                    input = EXPR_PLACEHOLDER
                    preview = ""
                }
            }
            Key.NOOP -> {
                // Nothing
            }
            else -> {
                input = input + key.inputValue()
                println("updated = " + input)
            }
        }

        if (key != Key.EVAL) {
            val inputEval = eval()
            if (!inputEval.isError) {
                val previewStr = exprToString(inputEval)
                if (previewStr != input)
                    preview = exprToString(inputEval)
            }
        }
    }

    fun directInput(input: String) {
        onKey(Key.CLEAR)
        this.input = this.input + input
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
        val input = input

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