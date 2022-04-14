package org.cerion.symcalcapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.cerion.symcalcapp.ui.theme.SymCalcTheme

val ButtonColor = Color(0xFF111111)

@Composable
fun KeyPad(onKeyPress: (key: Key) -> Unit) {
    Row(Modifier.fillMaxWidth().background(ButtonColor)) {
        Column(Modifier.weight(0.75f)) {
            Row(Modifier.weight(1.0f)) {
                Key("7") { onKeyPress(Key.NUM_7) }
                Key("8") { onKeyPress(Key.NUM_8) }
                Key("9") { onKeyPress(Key.NUM_9) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("4") { onKeyPress(Key.NUM_4) }
                Key("5") { onKeyPress(Key.NUM_5) }
                Key("6") { onKeyPress(Key.NUM_6) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("1") { onKeyPress(Key.NUM_1) }
                Key("2") { onKeyPress(Key.NUM_2) }
                Key("3") { onKeyPress(Key.NUM_3) }
            }
            Row(Modifier.weight(1.0f)) {
                Key("0") { onKeyPress(Key.NUM_0) }
                Key(".") { onKeyPress(Key.DOT) }
                Key("=") { onKeyPress(Key.EVAL) }
            }
        }
        Column(Modifier.weight(0.25f).fillMaxHeight()) {
            Key("DEL") { onKeyPress(Key.DEL) }
            Key("/") { onKeyPress(Key.DIVIDE) }
            Key("*") { onKeyPress(Key.TIMES) }
            Key("-") { onKeyPress(Key.MINUS) }
            Key("+") { onKeyPress(Key.PLUS) }
            Key("DEBUG") { onKeyPress(Key.DEBUG) }
        }
    }
}

@Composable
fun KeyPadExt(onKeyPress: (key: Key) -> Unit) {
    Column {
        Row(Modifier.fillMaxWidth().background(ButtonColor).weight(0.25f)) {
            Key("Sin") { onKeyPress(Key.SIN) }
            Key("Cos") { onKeyPress(Key.COS) }
            Key("Tan") { onKeyPress(Key.TAN) }
        }
        Row(Modifier.fillMaxWidth().background(ButtonColor).weight(0.25f)) {
            Key("Ln") { onKeyPress(Key.LN) }
            Key("Log") { onKeyPress(Key.LOG) }
            Key("Sqrt") { onKeyPress(Key.SQRT) }
        }
        Row(Modifier.fillMaxWidth().background(ButtonColor).weight(0.25f)) {
            Key("Pi") { onKeyPress(Key.PI) }
            Key("E") { onKeyPress(Key.E) }
            Key("^") { onKeyPress(Key.POW) }
        }
        Row(Modifier.fillMaxWidth().background(ButtonColor).weight(0.25f)) {
            Key("(") { onKeyPress(Key.BRACKET_LEFT) }
            Key(")") { onKeyPress(Key.BRACKET_RIGHT) }
            Key("!") { onKeyPress(Key.FACTORIAL) }
        }
    }
}

@Composable
fun ColumnScope.Key(label: String, onKeyPress:() -> Unit) {
    Button(onClick = { onKeyPress() }, Modifier.fillMaxWidth().weight(1f),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor))
    {
        Text(text = label, color = Color.White)
    }
}

@Composable
fun RowScope.Key(label: String, onKeyPress:() -> Unit) {
    Button(onClick = { onKeyPress() }, Modifier.fillMaxHeight().weight(1f),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor))
    {
        Text(text = label, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SymCalcTheme {
        KeyPad(onKeyPress = {})
        //KeyPadExt(onKeyPress = {})
    }
}