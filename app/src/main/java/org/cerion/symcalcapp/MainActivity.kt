package org.cerion.symcalcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import org.cerion.symcalc.expression.Expr
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SymCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator(initialDisplay: String = "") {

    var display by remember { mutableStateOf(initialDisplay) }
    var preview by remember {
        mutableStateOf(if (initialDisplay.isNotEmpty()) Expr.parse(initialDisplay).eval().toString() else "")
    }

    val onClick = { key: Key ->
        when(key) {
            Key.DEL -> {
                if (display.isNotEmpty())
                    display = display.substring(0, display.length - 1)
            }
            Key.EVAL -> {
                // TODO fix precision 8.05 - 5
                val inputExpr = Expr.parse(display)
                val result = inputExpr.eval()

                if (result.isError)
                    preview = result.toString()
                else {
                    display = result.toString()
                    preview = ""
                }
            }
            else -> {
                display += key.inputValue()
            }
        }

        if (key != Key.EVAL) {
            val inputEval = Expr.parse(display).eval()
            if (!inputEval.isError && inputEval.toString() != display)
                preview = inputEval.toString()
        }
    }

    Column {
        Text(text = display,
            Modifier.fillMaxWidth(),
            fontSize = 40.sp,
            textAlign = TextAlign.Right)
        Text(text = preview,
            Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            textAlign = TextAlign.Right)
        KeyPad(onClick)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SymCalcTheme {
        Calculator("2+2")
    }
}