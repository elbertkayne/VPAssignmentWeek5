package com.arielelbert.vpassignmentweek5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arielelbert.vpassignmentweek5.ui.theme.VPAssignmentWeek5Theme
import com.arielelbert.vpassignmentweek5.view.ExpenseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VPAssignmentWeek5Theme {
                ExpenseScreen()
            }
        }
    }
}