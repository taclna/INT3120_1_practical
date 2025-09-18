package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Dùng Box để MainPart ở giữa, SubPart ở cuối
                    Box(
                        modifier = Modifier.fillMaxSize(),

                        ) {
                        MainPart(
                            full_name = "LE CHI ANH TUAN",
                            title = "Student",
                            modifier = Modifier.align(Alignment.Center) // căn giữa
                        )

                        SubPart(
                            phone_number = "0981167912",
                            social_media_handle = "tacl_na",
                            email_address = "23020703@vnu.edu.vn",
                            modifier = Modifier
                                .align(Alignment.BottomCenter) // xuống cuối cùng
                                .padding(bottom = 24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainPart(full_name: String, title: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.ic_launcher_background)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Text(
            text = full_name,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp)
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun SubPart(
    phone_number: String,
    social_media_handle: String,
    email_address: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubPartCompose(
            image_vector = Icons.Filled.Phone,
            text = phone_number
        )

        SubPartCompose(
            image_vector = Icons.Filled.Share,
            text = social_media_handle
        )

        SubPartCompose(
            image_vector = Icons.Filled.Email,
            text = email_address
        )
    }
}

@Composable
fun SubPartCompose(image_vector: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp),   // bỏ fillMaxWidth
    ) {
        Icon(
            imageVector = image_vector,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
    }
}


