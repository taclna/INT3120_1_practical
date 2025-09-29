package com.example.unit3_pathway3_30days.ui.theme


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.unit3_pathway3_30days.data.WellnessTip

@Composable
fun WellnessScreen(tips: List<WellnessTip>) {
    LazyColumn {
        items(tips.size) { index ->
            TipCard(tip = tips[index])
        }
    }
}
