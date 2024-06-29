package com.example.mathsworkout.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathsworkout.R
import com.example.mathsworkout.ui.theme.DarkGreen
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.random.Random

@Composable
fun QuizScreen(modifier: Modifier) {
    val focus = LocalFocusManager.current

    var numberRange by rememberSaveable {
        mutableIntStateOf(Random.nextInt(1, 11))
    }
    var questionNo by rememberSaveable {
        mutableIntStateOf(1)
    }

    val options = hashMapOf("2 min" to 120000L, "3 min" to 180000L, "5 min" to 300000L)


    var selectedIndex by rememberSaveable { mutableLongStateOf(-1) }


    var startNumberRange by rememberSaveable {
        mutableStateOf("")
    }
    var endNumberRange by rememberSaveable {
        mutableStateOf("")
    }
    var answer by rememberSaveable {
        mutableStateOf("")
    }
    var question by rememberSaveable {
        mutableIntStateOf(0)
    }

    var points by rememberSaveable {
        mutableIntStateOf(0)
    }

    var tableType by rememberSaveable {
        mutableIntStateOf(0)
    }
    when (tableType) {
        0 -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                item {
                    Spacer(Modifier.height(48.dp))
                    Text(
                        text = "Quiz",
                        fontSize = 64.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif,
                        textDecoration = TextDecoration.Underline
                    )
                    Spacer(Modifier.height(48.dp))
                    Row {
                        options.forEach { (content, value) ->
                            OutlinedButton(
                                onClick = {
                                    selectedIndex = value
                                },
                                enabled = selectedIndex != value,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    disabledContentColor = DarkGreen,
                                ),
                                border = BorderStroke(1.dp, DarkGreen)
                            ) {

                                SmallText(text = content)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(Modifier.height(68.dp))
                    CustomOutlineTextFieldComposable(
                        dataValue = startNumberRange,
                        dataValueCheck = null,
                        onUpdate = { startNumberRange = it },
                        onReset = { startNumberRange = "" },
                        forSupportingText = {
                            if (startNumberRange.isEmpty()) {
                                SmallText(text = stringResource(id = R.string.required))
                            } else if (endNumberRange.isNotEmpty()
                                && startNumberRange.toLong() > endNumberRange.toLong()
                            ) {
                                SmallText(
                                    text = stringResource(
                                        R.string.number_must_be_less_than_or_equal_to,
                                        endNumberRange
                                    )
                                )
                            }
                        },
                        focus = focus,
                        onLabel = { SmallText(text = stringResource(R.string.starting_number_range)) },
                        unFocusColor = if (startNumberRange.isNotEmpty()) Color.Gray else Color.Red,
                        modifier = Modifier
                    )
                    Spacer(Modifier.height(108.dp))
                    CustomOutlineTextFieldComposable(
                        dataValue = endNumberRange,
                        dataValueCheck = null,
                        onUpdate = { endNumberRange = it },
                        onReset = { endNumberRange = "" },
                        forSupportingText = {
                            if (endNumberRange.isEmpty()) {
                                SmallText(text = stringResource(id = R.string.required))
                            } else if (startNumberRange.isNotEmpty()
                                && startNumberRange.toLong() > endNumberRange.toLong()
                            ) {
                                SmallText(
                                    text = stringResource(
                                        R.string.number_must_be_greater_than_or_equal_to,
                                        startNumberRange
                                    )
                                )
                            }
                        },
                        focus = focus,
                        onLabel = { SmallText(text = stringResource(R.string.ending_number_range)) },
                        unFocusColor = if (endNumberRange.isNotEmpty()) Color.Gray else Color.Red,
                        modifier = Modifier
                    )
                    if (selectedIndex != -1L && startNumberRange.isNotEmpty() && endNumberRange.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(104.dp))
                        Row {
                            ButtonComposable(
                                onTableUpdate = { tableType = 1 },
                                tableName = stringResource(R.string.nt)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ButtonComposable(
                                onTableUpdate = { tableType = 2 },
                                tableName = stringResource(R.string.pt)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ButtonComposable(
                                onTableUpdate = { tableType = 3 },
                                tableName = stringResource(R.string.mt)
                            )
                        }
                    }
                    Spacer(Modifier.height(108.dp))
                }
            }
        }

        1 -> {
            question = Random.nextInt(startNumberRange.toInt(), endNumberRange.toInt()+1)
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ElevatedCard(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 16.dp, end = 16.dp, top = 100.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 60.dp,
                        ),
                    ) {
                        Column {
                            QuestionNumberComposable(questionNo)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                TextToDisplayComposable(
                                    text = stringResource(
                                        R.string.normal_format,
                                        question,
                                        numberRange
                                    ),
                                    fontSize = 48.sp,
                                    color = Color.Unspecified,
                                    modifier = Modifier
                                )
                                CustomOutlineTextFieldComposable(
                                    dataValue = answer,
                                    dataValueCheck = null,
                                    onUpdate = { answer = it },
                                    onReset = { answer = "" },
                                    focus = focus,
                                    forSupportingText = {
                                        if (answer.isEmpty()) {
                                            SmallText(stringResource(R.string.required))
                                        }
                                    },
                                    modifier = Modifier
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                if (answer.isNotEmpty()) {
                                    ButtonComposable(
                                        onTableUpdate = {
                                            if (answer == (question * numberRange).toString()) {
                                                points++
                                            }
                                            answer = ""
                                            questionNo++
                                            question = Random.nextInt(
                                                startNumberRange.toInt(),
                                                endNumberRange.toInt()+1
                                            )
                                            selectedIndex = -1
                                            numberRange = Random.nextInt(1, 11)
                                        },
                                        tableName = "Next"
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(108.dp))

                }
            }
            CountDownTimer(
                initialTime = selectedIndex,
                onTick = { }
            ) {
                questionNo--
                tableType = 4
            }
        }

        2 -> {
            question = Random.nextInt(startNumberRange.toInt(), endNumberRange.toInt()+1)
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ElevatedCard(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 16.dp, end = 16.dp, top = 100.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 60.dp,
                        ),
                    ) {
                        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                            QuestionNumberComposable(questionNo)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                TextToDisplayComposable(
                                    text = stringResource(
                                        R.string.power_format,
                                        question,
                                        numberRange
                                    ),
                                    fontSize = 48.sp,
                                    color = Color.Unspecified,
                                    modifier = Modifier
                                )
                                CustomOutlineTextFieldComposable(
                                    dataValue = answer,
                                    dataValueCheck = null,
                                    onUpdate = { answer = it },
                                    onReset = { answer = "" },
                                    focus = focus,
                                    forSupportingText = {
                                        if (answer.isEmpty()) {
                                            SmallText(stringResource(R.string.required))
                                        }
                                    },
                                    modifier = Modifier
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                if (answer.isNotEmpty()) {
                                    ButtonComposable(
                                        onTableUpdate = {
                                            if (answer == ((question.toDouble()).pow(numberRange.toDouble())).toLong()
                                                    .toString()
                                            ) {
                                                points++
                                            }
                                            answer = ""
                                            questionNo++
                                            question = Random.nextInt(
                                                startNumberRange.toInt(),
                                                endNumberRange.toInt()+1
                                            )
                                            selectedIndex = -1
                                            numberRange = Random.nextInt(1, 11)
                                        },
                                        tableName = "Next"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(108.dp))

                }
            }
            CountDownTimer(
                initialTime = selectedIndex,
                onTick = { }
            ) {
                questionNo--
                tableType = 4
            }
        }

        3 -> {
            var qType by rememberSaveable {
                mutableIntStateOf(Random.nextInt(1, 3))
            }
            question = Random.nextInt(startNumberRange.toInt(), endNumberRange.toInt()+1)
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ElevatedCard(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 16.dp, end = 16.dp, top = 100.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 60.dp,
                        ),
                    ) {
                        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                            QuestionNumberComposable(questionNo)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                TextToDisplayComposable(
                                    text = if (qType == 1) stringResource(
                                        R.string.normal_format,
                                        question,
                                        numberRange
                                    ) else stringResource(
                                        R.string.power_format,
                                        question,
                                        numberRange
                                    ),
                                    fontSize = 48.sp,
                                    color = Color.Unspecified,
                                    modifier = Modifier
                                )
                                CustomOutlineTextFieldComposable(
                                    dataValue = answer,
                                    dataValueCheck = null,
                                    onUpdate = { answer = it },
                                    onReset = { answer = "" },
                                    focus = focus,
                                    forSupportingText = {
                                        if (answer.isEmpty()) {
                                            SmallText(stringResource(R.string.required))
                                        }
                                    },
                                    modifier = Modifier
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                if (answer.isNotEmpty()) {
                                    ButtonComposable(
                                        onTableUpdate = {
                                            if (qType == 1) {
                                                if (answer == (question * numberRange).toString()) {
                                                    points++
                                                }
                                            } else {
                                                if (answer == ((question.toDouble()).pow(numberRange.toDouble())).toLong()
                                                        .toString()
                                                ) {
                                                    points++
                                                }
                                            }
                                            qType = Random.nextInt(1, 3)
                                            answer = ""
                                            questionNo++
                                            question = Random.nextInt(
                                                startNumberRange.toInt(),
                                                endNumberRange.toInt()+1
                                            )
                                            selectedIndex = -1
                                            numberRange = Random.nextInt(1, 11)
                                        },
                                        tableName = "Next"
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(108.dp))

                }

            }
            CountDownTimer(
                initialTime = selectedIndex,
                onTick = { }
            ) {
                questionNo--
                tableType = 4
            }
        }

        4 -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                item {
                    Spacer(Modifier.height(48.dp))
                    Text(
                        text = "Result",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(Modifier.height(32.dp))
                    Text(
                        text = "You Attempted $questionNo Questions",
                        fontSize = 48.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(Modifier.height(28.dp))
                    Text(
                        text = "Correct Answers = $points",
                        fontSize = 36.sp,
                        color = DarkGreen,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(Modifier.height(28.dp))
                    Text(
                        text = "Correct Percentage = ${
                            String.format(
                                "%.2f",
                                ((points * 100).toDouble() / questionNo)
                            )
                        }%",
                        fontSize = 32.sp,
                        color = DarkGreen,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(Modifier.height(28.dp))
                    Text(
                        text = "Wrong Answers = ${questionNo - points}",
                        fontSize = 36.sp,
                        color = Color.Red,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(Modifier.height(28.dp))
                    ButtonComposable(
                        onTableUpdate = {
                            tableType = 0
                            questionNo = 1
                        },
                        tableName = "Finish Quiz"
                    )
                    Spacer(Modifier.height(108.dp))
                }
            }

        }
    }

}

@Composable
fun CountDownTimer(
    initialTime: Long,
    onTick: (Long) -> Unit,
    onFinish: () -> Unit
) {
    var timeLeft by rememberSaveable { mutableLongStateOf(initialTime) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1000
            onTick(timeLeft)
        }
        onFinish()
    }
    Text(
        text = "${timeLeft / 60000} : ${timeLeft % 60000 / 1000} min",
        fontSize = 40.sp,
        color = if (timeLeft <= 10000) Color.Red else DarkGreen,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen(modifier = Modifier)
}