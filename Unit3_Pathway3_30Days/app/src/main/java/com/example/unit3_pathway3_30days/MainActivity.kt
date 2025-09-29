package com.example.unit3_pathway3_30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit3_pathway3_30days.data.WellnessTip
import com.example.unit3_pathway3_30days.ui.theme.WellnessScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tips = generateTips()
            WellnessScreen(tips)
        }
    }
}

fun generateTips(): List<WellnessTip> {
    return List(30) { i ->
        WellnessTip(
            day = i + 1,
            title = "Wellness Tip ${i + 1}",
            description = "Hãy thử một thói quen sống khoẻ mới cho ngày ${i + 1}.",
            imageRes = R.drawable.sample_image
        )
    }
}
