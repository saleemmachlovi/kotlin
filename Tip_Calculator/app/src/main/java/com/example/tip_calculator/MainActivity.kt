package com.example.tip_calculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tip_calculator.ui.theme.Tip_CalculatorTheme
import java.text.NumberFormat
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { // jetpack compose function that defines ui
            Tip_CalculatorTheme {
                Surface{
                    TipTimeLayout()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tipInput by remember { mutableStateOf("")}
    val tipPercent = tipInput.toDoubleOrNull() ?: 15.0
    var roundUp by remember { mutableStateOf(false) }
    val tip = calculateTip(amount, tipPercent, roundUp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.calculate_tip),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                letterSpacing = 0.15.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = R.string.bill_amount,
            leadingIcon = Icons.Filled.Money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        EditNumberField(
            value = tipInput,
            onValueChange = { tipInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = R.string.how_was_the_service,
            leadingIcon = Icons.Filled.Percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        RoundTheTipRow(
            roundUp = roundUp,
            onRoundChanged = { roundUp = it },
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
//            style = TextStyle(
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                letterSpacing = 0.15.sp
//            )
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@VisibleForTesting
internal fun calculateTip( amount: Double, tipPercent: Double, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}
@Composable
fun EditNumberField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    @StringRes label: Int,
    keyboardOptions : KeyboardOptions,
    leadingIcon: ImageVector
){
    TextField(
        value = value,
        label = { Text(stringResource(label)) },
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) }
    )
}


@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundChanged: (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp), // only height
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // pushes Text left, Switch right
    ){
        Text(text = stringResource(R.string.round_up_tip),
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.15.sp
            )
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundChanged
        )
    }
}