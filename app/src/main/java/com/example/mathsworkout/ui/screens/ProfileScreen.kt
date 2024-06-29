package com.example.mathsworkout.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathsworkout.ComposableRiveAnimation
import com.example.mathsworkout.R
import com.example.mathsworkout.UserSignIn
import com.example.mathsworkout.userData

@Composable
fun ProfileScreen(
    animation: Int = R.raw.avatar_prototype,
    stateMachineName: String = "State Machine 1",
    modifier: Modifier
) {
    val context = LocalContext.current
    val currentUserData = context.openFileInput(userData).bufferedReader().useLines { lines ->
        lines.fold("") { some, text ->
            "$some\n$text"
        }.split("-")

    }
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(48.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .size(180.dp)
                    .clip(CircleShape)
                    .padding(end = 12.dp)
                    .scale(2f)

            ) {
//                        Spacer(Modifier.height(68.dp))
                ComposableRiveAnimation(
                    modifier = Modifier,
                    animation = animation,
                    stateMachineName = stateMachineName
                ) { view ->
                    view.setNumberState(
                        stateMachineName,
                        "Bodies",
                        value = currentUserData[0].toFloat()
                    )
                    view.setNumberState(
                        stateMachineName,
                        "Eyes",
                        value = currentUserData[1].toFloat()
                    )
                    view.setNumberState(
                        stateMachineName,
                        "Mouth",
                        value = currentUserData[2].toFloat()
                    )
                }
            }
            Spacer(modifier = Modifier.height(68.dp))
            Text(
                text = currentUserData[3],
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(108.dp))
            ElevatedButton(
                onClick = {
                    context.startActivity(Intent(context, UserSignIn::class.java))
                    context.deleteFile(userData)
                },
                elevation = ButtonDefaults.buttonElevation(
                    12.dp,
                    0.dp,
                    6.dp,
                    8.dp,
                    (-4).dp
                )
            ) {
                Text(text = "Edit")
            }
            Spacer(Modifier.height(108.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(modifier = Modifier)
}