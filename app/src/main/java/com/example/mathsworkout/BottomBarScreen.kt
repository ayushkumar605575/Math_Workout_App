package com.example.mathsworkout

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Learn : BottomBarScreen(
        "learn",
        "Learn",
        R.drawable.baseline_menu_book_24
    )

    object Practice : BottomBarScreen(
        "practice",
        "Practice",
        R.drawable.baseline_note_alt_24
    )

    object Quiz : BottomBarScreen(
        "quiz",
        "Quiz",
        R.drawable.baseline_quiz_24
    )

    object Profile : BottomBarScreen(
        "profile",
        "Profile",
        R.drawable.baseline_account_circle_24
    )
}
