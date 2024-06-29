package com.example.mathsworkout.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathsworkout.R
import com.example.mathsworkout.ui.theme.DarkGreen
import com.example.mathsworkout.ui.theme.LightBlue
import kotlin.math.pow
import kotlin.random.Random

@Composable
fun PracticeScreen(modifier: Modifier) {
    val focus = LocalFocusManager.current

    var startNumberRange by rememberSaveable {
        mutableStateOf("")
    }
    var endNumberRange by rememberSaveable {
        mutableStateOf("")
    }
    var question by rememberSaveable {
        mutableLongStateOf(0)
    }
    var numberRange by rememberSaveable {
        mutableIntStateOf(Random.nextInt(1, 11))
    }

    var numberQ by rememberSaveable {
        mutableStateOf("")
    }

    var answer by rememberSaveable {
        mutableStateOf("")
    }

    var answerCheck by rememberSaveable {
        mutableIntStateOf(0)
    }

    var correctAnswer by rememberSaveable {
        mutableStateOf(false)
    }


    var tableType by rememberSaveable {
        mutableIntStateOf(0)
    }

    var questionNo by rememberSaveable {
        mutableIntStateOf(1)
    }

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        item {

            when (tableType) {
                0 -> {
                    Spacer(Modifier.height(48.dp))
                    Text(
                        text = "Practice",
                        fontSize = 64.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif,
                        textDecoration = TextDecoration.Underline
                    )
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
                    Spacer(Modifier.height(112.dp))
                    CustomOutlineTextFieldComposable(
                        dataValue = numberQ,
                        dataValueCheck = null,
                        onUpdate = { numberQ = it },
                        onReset = { numberQ = "" },
                        focus = focus,
                        onLabel = { SmallText(stringResource(R.string.enter_no_of_questions)) },
                        forSupportingText = {
                            if (numberQ.isEmpty()) {
                                SmallText(text = stringResource(id = R.string.required))
                            } else if (numberQ.toLong() <= 0) {
                                SmallText(text = stringResource(R.string.this_value_must_be_greater_than_or_equal_to_1))
                            }
                        },
                        modifier = Modifier
                    )
                    Spacer(Modifier.height(80.dp))
                    if (numberQ.isNotEmpty()
                        && startNumberRange.isNotEmpty()
                        && endNumberRange.isNotEmpty()
                        && (startNumberRange.toLong() <= endNumberRange.toLong())
                        && (numberQ.toLong() > 0)
                    ) {
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
                }

                1 -> {
                    question = Random.nextLong(
                        startNumberRange.toLong(),
                        (endNumberRange.toLong()) + 1
                    )
                    var qLeft by rememberSaveable {
                        mutableIntStateOf(numberQ.toInt())
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                                Spacer(modifier = Modifier.height(8.dp))
                                if (answerCheck == 1) {
                                    TextToDisplayComposable(
                                        text = stringResource(id = R.string.correct),
                                        fontSize = 32.sp,
                                        color = DarkGreen,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else if (answerCheck == 2) {
                                    TextToDisplayComposable(
                                        text = stringResource(id = R.string.wrong),
                                        fontSize = 32.sp,
                                        color = Color.Red,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    TextToDisplayComposable(
                                        text = stringResource(
                                            R.string.normal_ans,
                                            question * numberRange
                                        ),
                                        fontSize = 32.sp,
                                        color = DarkGreen,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
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
                                        dataValueCheck = answerCheck,
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
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    if (answer.isNotEmpty()) {
                                        ButtonComposable(
                                            onTableUpdate = {
                                                if (answerCheck == 0) {
                                                    answerCheck =
                                                        if ((question * numberRange).toString() == answer) 1 else 2
                                                } else {
                                                    if (qLeft == 1) {
                                                        tableType = 0
                                                    }
                                                    answer = ""
                                                    question = Random.nextLong(
                                                        startNumberRange.toLong(),
                                                        (endNumberRange.toLong()) + 1
                                                    )
                                                    questionNo++
                                                    qLeft--
                                                    numberRange = Random.nextInt(1, 11)
                                                    correctAnswer = !correctAnswer
                                                    answerCheck = 0
                                                }

                                            },
                                            tableName = if (qLeft == 1 && answerCheck != 0) "Finish Practice"
                                            else if (answerCheck != 0) "Next"
                                            else "Check"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    question = Random.nextLong(
                        startNumberRange.toLong(),
                        (endNumberRange.toLong()) + 1
                    )
                    var qLeft by rememberSaveable {
                        mutableIntStateOf(numberQ.toInt())
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                                Spacer(modifier = Modifier.height(8.dp))
                                if (answerCheck == 1) {
                                    TextToDisplayComposable(
                                        text = stringResource(id = R.string.correct),
                                        fontSize = 32.sp,
                                        color = DarkGreen,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else if (answerCheck == 2) {
                                    TextToDisplayComposable(
                                        text = stringResource(id = R.string.wrong),
                                        fontSize = 32.sp,
                                        color = Color.Red,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    TextToDisplayComposable(
                                        text = stringResource(
                                            R.string.power_ans, (question.toDouble()).pow(
                                                numberRange.toDouble()
                                            ).toLong()
                                        ),
                                        fontSize = 32.sp,
                                        color = DarkGreen,
                                        modifier = Modifier
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
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
                                        dataValueCheck = answerCheck,
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
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    if (answer.isNotEmpty()) {
                                        ButtonComposable(
                                            onTableUpdate = {
                                                if (answerCheck == 0) {
                                                    answerCheck =
                                                        if ((question.toDouble()).pow(numberRange.toDouble())
                                                                .toLong().toString() == answer
                                                        ) 1 else 2
                                                } else {
                                                    if (qLeft == 1) {
                                                        tableType = 0
                                                    }
                                                    answer = ""
                                                    question = Random.nextLong(
                                                        startNumberRange.toLong(),
                                                        (endNumberRange.toLong()) + 1
                                                    )
                                                    questionNo++
                                                    qLeft--
                                                    numberRange = Random.nextInt(1, 11)
                                                    correctAnswer = !correctAnswer
                                                    answerCheck = 0
                                                    Log.i("msg", qLeft.toString())
                                                }

                                            },
                                            tableName = if (qLeft == 1 && answerCheck != 0) "Finish Practice"
                                            else if (answerCheck != 0) "Next"
                                            else "Check"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                3 -> {
                    question = Random.nextLong(
                        startNumberRange.toLong(),
                        (endNumberRange.toLong()) + 1
                    )
                    var qLeft by rememberSaveable {
                        mutableIntStateOf(numberQ.toInt())
                    }
                    var qType by rememberSaveable {
                        mutableIntStateOf(Random.nextInt(1, 3))
                    }

                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                                Spacer(modifier = Modifier.height(8.dp))
                                if (answerCheck == 1) {
                                    TextToDisplayComposable(
                                        text = stringResource(R.string.correct),
                                        32.sp,
                                        DarkGreen,
                                        Modifier.fillMaxWidth()
                                    )
                                } else if (answerCheck == 2) {
                                    TextToDisplayComposable(
                                        text = stringResource(R.string.wrong),
                                        32.sp,
                                        Color.Red,
                                        Modifier.fillMaxWidth()
                                    )
                                    TextToDisplayComposable(
                                        text = if (qType == 1) stringResource(
                                            R.string.normal_ans,
                                            question * numberRange
                                        ) else stringResource(
                                            R.string.power_ans, (question.toDouble()).pow(
                                                numberRange.toDouble()
                                            ).toLong()
                                        ),
                                        32.sp,
                                        DarkGreen,
                                        Modifier.fillMaxWidth()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
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
                                        Color.Unspecified,
                                        modifier = Modifier
                                    )
                                    CustomOutlineTextFieldComposable(
                                        dataValue = answer,
                                        dataValueCheck = answerCheck,
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
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    if (answer.isNotEmpty()) {
                                        ButtonComposable(
                                            onTableUpdate = {
                                                if (answerCheck == 0) {
                                                    answerCheck =
                                                        if (qType == 1) {
                                                            if ((question * numberRange).toString() == answer) 1 else 2

                                                        } else {
                                                            if ((question.toDouble()).pow(
                                                                    numberRange.toDouble()
                                                                )
                                                                    .toLong().toString() == answer
                                                            ) 1 else 2
                                                        }
                                                } else {
                                                    if (qLeft == 1) {
                                                        tableType = 0
                                                    }
                                                    answer = ""
                                                    question = Random.nextLong(
                                                        startNumberRange.toLong(),
                                                        (endNumberRange.toLong()) + 1
                                                    )
                                                    questionNo++
                                                    qLeft--
                                                    qType = Random.nextInt(1, 3)
                                                    numberRange = Random.nextInt(1, 11)
                                                    correctAnswer = !correctAnswer
                                                    answerCheck = 0
                                                }
                                            },
                                            tableName = if (qLeft == 1 && answerCheck != 0) "Finish Practice" else if (answerCheck != 0) "Next" else "Check"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(108.dp))
        }
    }
}

@Composable
fun CustomOutlineTextFieldComposable(
    dataValue: String,
    dataValueCheck: Int?,
    onUpdate: (String) -> Unit,
    onReset: () -> Unit,
    onLabel: @Composable () -> Unit = { SmallText(text = stringResource(R.string.ans)) },
    forSupportingText: @Composable () -> Unit,
    focus: FocusManager,
    unFocusColor: Color = if (dataValueCheck == 1) DarkGreen else Color.Red,
    keyboardType: KeyboardType = KeyboardType.Number,
    modifier: Modifier
) {
    OutlinedTextField(
        value = dataValue,
        maxLines = 1,
        trailingIcon = {
            if (dataValue.isNotEmpty() && dataValueCheck == 0) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier
                        .clickable { onReset() }
                )
            }
        },
        shape = CircleShape,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = if (dataValueCheck == 1) DarkGreen else if (dataValueCheck == 2) Color.Red else LightBlue,
            unfocusedIndicatorColor = unFocusColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Blue,
            focusedLabelColor = if (dataValueCheck == 1) DarkGreen else if (dataValueCheck == 2) Color.Red else LightBlue,
            focusedSupportingTextColor = Color.Red,
            unfocusedSupportingTextColor = Color.Red
        ),
        supportingText = {
            forSupportingText()
        },
        onValueChange = { onUpdate(it) },
        label = { onLabel() },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
    )
}

@Composable
fun SmallText(text: String) {
    Text(text = text)
}

@Composable
fun QuestionNumberComposable(questionNo: Int) {
    Text(
        text = stringResource(id = R.string.question_no, questionNo),
        fontSize = 48.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Cursive,
        maxLines = 1,
        minLines = 1,
        softWrap = true,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextToDisplayComposable(text: String, fontSize: TextUnit, color: Color, modifier: Modifier) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        maxLines = 3,
        minLines = 1,
        softWrap = true,
        textAlign = TextAlign.Center,
        lineHeight = 40.sp,
        modifier = modifier.wrapContentSize()
    )
}

@Composable
fun ButtonComposable(onTableUpdate: () -> Unit, tableName: String) {
    ElevatedButton(
        onClick = onTableUpdate,
        elevation = ButtonDefaults.buttonElevation(
            12.dp,
            0.dp,
            6.dp,
            8.dp,
            (-4).dp
        )
    ) {
        Text(text = tableName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PracticeScreenPreview() {
    PracticeScreen(Modifier)
//    TextToDisplayComposable(text = "Correct answer is 56456456", fontSize = 32.sp, color = DarkGreen, modifier = Modifier)
}