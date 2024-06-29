package com.example.mathsworkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.mathsworkout.ui.screens.CustomOutlineTextFieldComposable
import com.example.mathsworkout.ui.screens.SmallText
import com.example.mathsworkout.ui.theme.MathsWorkoutTheme
import java.io.File
import kotlin.math.ceil

internal const val userData = "UserData.txt"

class UserSignIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("msg", File("${this.dataDir.absolutePath}/files/$userData").length().toString())
        if (File("${this.dataDir.absolutePath}/files/$userData").length() > 0) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContent {
            MathsWorkoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SignInScreen(
                        animation = R.raw.avatar_prototype,
                        stateMachineName = "State Machine 1",
                    )
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    animation: Int,
    stateMachineName: String,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current

    val focus = LocalFocusManager.current

    var userDetails by rememberSaveable {
        mutableStateOf(false)
    }

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var avatarSelection by rememberSaveable {
        mutableStateOf(false)
    }

    var body by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var eye by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    var mouth by rememberSaveable {
        mutableFloatStateOf(0f)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(54.dp))
            Column(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .size(180.dp)
                    .clip(CircleShape)
                    .scale(2f)
                    .padding(end = 2.dp)
                    .clickable {
                        avatarSelection = !avatarSelection
                    }
            ) {
                ComposableRiveAnimation(
                    modifier = modifier,
                    animation = animation,
                    stateMachineName = stateMachineName
                ) { view ->
                    view.setNumberState(
                        stateMachineName,
                        "Bodies",
                        value = body
                    )
                    view.setNumberState(
                        stateMachineName,
                        "Eyes",
                        value = eye
                    )
                    view.setNumberState(
                        stateMachineName,
                        "Mouth",
                        value = mouth
                    )
                }
            }
            if (avatarSelection) {
                Spacer(modifier = Modifier.height(56.dp))
                AvatarStyle("Hair", 6, body) { body = it }
                Spacer(modifier = Modifier.height(4.dp))
                AvatarStyle("Eye", 5, eye) {
                    eye = ceil(it)
                    Log.i("Msg", ceil(it).toString())
                }
                Spacer(modifier = Modifier.height(4.dp))
                AvatarStyle("Mouth", 2, mouth) { mouth = it }
                Spacer(modifier = Modifier.height(56.dp))
                ElevatedButton(
                    onClick = {
                        avatarSelection = !avatarSelection
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        12.dp,
                        0.dp,
                        6.dp,
                        8.dp,
                        (-4).dp
                    )
                ) {
                    Text("Done")
                }
            } else {
                Spacer(Modifier.height(76.dp))
                CustomOutlineTextFieldComposable(
                    dataValue = name,
                    dataValueCheck = null,
                    onUpdate = { name = it },
                    onReset = { name = "" },
                    forSupportingText = {
                        if (name.isEmpty()) {
                            SmallText(text = stringResource(id = R.string.required))
                        }
                    },
                    focus = focus,
                    unFocusColor = if (name.isNotEmpty()) Color.Gray else Color.Red,
                    onLabel = { SmallText(text = stringResource(R.string.enter_your_name)) },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier
                )
                Spacer(Modifier.height(96.dp))
                ElevatedButton(
                    onClick = {
                        if (name.isEmpty()) {
                            alert(context, "Please Enter a Name")
                        } else if (eye == 0f) {
                            alert(context, "Avatar must have an Eye!!")
                        } else {
                            userDetails = !userDetails
                        }
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        12.dp,
                        0.dp,
                        6.dp,
                        8.dp,
                        (-4).dp
                    )
                ) {
                    Text(text = stringResource(R.string.finish))
                }
            }
            if (userDetails) {
                val fileContents = "$body-$eye-$mouth-$name"
                context.openFileOutput(userData, Context.MODE_PRIVATE).use {
                    it.write(fileContents.toByteArray())
                    it.close()
                }
                startActivity(
                    context,
                    Intent(context, MainActivity::class.java),
                    Bundle()
                )

            }
            Spacer(Modifier.height(108.dp))
        }
    }
}


internal fun alert(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}


@Composable
fun AvatarStyle(
    bodyPart: String,
    step: Int,
    face: Float,
    onFaceChange: (Float) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.select_style, bodyPart),
            fontSize = 22.sp,
            fontWeight = Bold
        )
        Slider(
            value = face,
            onValueChange = onFaceChange,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = step - 1,
            valueRange = 0f..step.toFloat(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewS(modifier: Modifier = Modifier) {
}