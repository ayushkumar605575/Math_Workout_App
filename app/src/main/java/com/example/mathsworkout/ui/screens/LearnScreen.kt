package com.example.mathsworkout.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathsworkout.R
import kotlin.math.pow


@Composable
fun LearnScreen(modifier: Modifier) {
    val focus = LocalFocusManager.current

    var number by rememberSaveable {
        mutableStateOf("")
    }
    var tableType by rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {


        when (tableType) {
            0 -> {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(Modifier.height(28.dp))
                        Text(
                            text = "Learn",
                            fontSize = 64.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Serif,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                    item {
                        Spacer(Modifier.height(68.dp))
                        CustomOutlineTextFieldComposable(
                            dataValue = number,
                            dataValueCheck = null,
                            onUpdate = { number = it },
                            onReset = { number = "" },
                            forSupportingText = {
                                if (number.isEmpty()) {
                                    SmallText(text = stringResource(R.string.required))
                                }
                            },
                            unFocusColor = if (number.isNotEmpty()) Color.Gray else Color.Red,
                            onLabel = { SmallText(text = stringResource(R.string.enter_a_number)) },
                            focus = focus,
                            modifier = Modifier
                        )
                    }
                    if (number.isNotEmpty()) {
                        item {
                            Spacer(Modifier.height(108.dp))
                            ElevatedButton(
                                onClick = {
                                    tableType = 1
                                },
                                elevation = ButtonDefaults.buttonElevation(
                                    12.dp,
                                    0.dp,
                                    6.dp,
                                    8.dp,
                                    (-4).dp
                                )
                            ) {
                                Text(
                                    text = "Normal Table",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                )
                            }
                            Spacer(Modifier.height(112.dp))
                            ElevatedButton(
                                onClick = {
                                    tableType = 2
                                },
                                elevation = ButtonDefaults.buttonElevation(
                                    12.dp,
                                    0.dp,
                                    6.dp,
                                    8.dp,
                                    (-4).dp
                                )
                            ) {
                                Text(
                                    text = "Power Table",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 32.sp
                                )
                            }
                            Spacer(Modifier.height(108.dp))
                        }
                    }
                }
            }


            1 -> {

                Table(
                    tableName = "Normal",
                    number = number,
                    tableFormat = R.string.normal_table,
                    tableType = tableType
                ) {
                    tableType = 0
                    number = ""
                }

            }


            2 -> {


                Table(
                    tableName = "Power",
                    number = number,
                    tableFormat = R.string.power_table,
                    tableType = tableType
                ) {
                    tableType = 0
                    number = ""
                }
            }


        }
    }
}


@Composable
fun Table(
    tableName: String,
    number: String,
    tableFormat: Int,
    tableType: Int,
    onTableType: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 60.dp,
        ),
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.table_of, tableName, number),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive,
                    maxLines = 2,
                    minLines = 1,
                    lineHeight = 64.sp,
                    textDecoration = TextDecoration.Underline,
                    softWrap = true
                )
                Spacer(Modifier.height(16.dp))
            }
            items(10) { item ->
                Text(
                    text = if (tableType == 1) {
                        stringResource(
                            tableFormat,
                            number,
                            item + 1,
                            (number.toLong()) * (item + 1)
                        )
                    } else {
                        stringResource(
                            tableFormat,
                            number,
                            item + 1,
                            ((number.toDouble()).pow(item + 1)).toLong()
                        )
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onTableType() }
                )
                Spacer(modifier = Modifier.height(24.dp))

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LearnScreenPreview() {
    LearnScreen(modifier = Modifier)
}