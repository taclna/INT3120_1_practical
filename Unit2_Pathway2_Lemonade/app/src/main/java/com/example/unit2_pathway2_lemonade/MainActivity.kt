package com.example.unit2_pathway2_lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit2_pathway2_lemonade.ui.theme.Unit2_Pathway2_LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit2_Pathway2_LemonadeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var step by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }
    var squeezeLimit by remember { mutableStateOf(0) }

    val imageRes: Int
    val textRes: Int
    val contentDescriptionRes: Int

    when (step) {
        1 -> {
            imageRes = R.drawable.lemon_tree
            textRes = R.string.tap_lemon_tree
            contentDescriptionRes = R.string.lemon_tree
        }
        2 -> {
            imageRes = R.drawable.lemon_squeeze
            textRes = R.string.keep_tapping_lemon
            contentDescriptionRes = R.string.lemon
        }
        3 -> {
            imageRes = R.drawable.lemon_drink
            textRes = R.string.tap_lemonade
            contentDescriptionRes = R.string.glass_lemonade
        }
        else -> {
            imageRes = R.drawable.lemon_restart
            textRes = R.string.tap_empty_glass
            contentDescriptionRes = R.string.empty_glass
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(id = contentDescriptionRes),
            modifier = Modifier
                .size(200.dp)
                .clickable {
                    when (step) {
                        1 -> {
                            step = 2
                            squeezeLimit = Random.nextInt(2, 5)
                            squeezeCount = 0
                        }
                        2 -> {
                            squeezeCount++
                            if (squeezeCount >= squeezeLimit) {
                                step = 3
                            }
                        }
                        3 -> step = 4
                        4 -> step = 1
                    }
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    Unit2_Pathway2_LemonadeTheme {
        LemonadeApp()
    }
}
