package com.arielelbert.vpassignmentweek5.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.arielelbert.vpassignmentweek5.model.Expense
import java.text.NumberFormat
import java.util.Locale

class ExpenseViewModel : ViewModel() {
    var expenseName by mutableStateOf("")
    var expenseAmount by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var editExpense by mutableStateOf<Expense?>(null)
    var editAmount by mutableStateOf("")
    var expenses = mutableStateOf(listOf<Expense>())
        private set
    val totalExpense : Int
        get() = expenses.value.sumOf { it.amount }
    fun Rupiah(amount: Int): String{
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(amount.toDouble()).replace("Rp", "Rp").replace(",00", "")
    }
    fun addExpense(){
        val name = expenseName.trim()
        val amount = expenseAmount.toIntOrNull() ?: 0
        if (amount <= 0){
            errorMessage = "Amount must be greater than 0"
            return
        }
        errorMessage = null
        val newExpense = Expense(
            id = (expenses.value.maxOfOrNull { it.id } ?: 0) + 1,
            name = if (name.isEmpty()) "Pengeluaran Lain" else name,
            amount = amount
        )
        expenses.value = expenses.value + newExpense
        expenseName = ""
        expenseAmount = ""
    }
    fun deleteExpense(expense: Expense){
        expenses.value = expenses.value.filter { it.id != expense.id }
    }
    fun startEdit(expense: Expense){
        editExpense = expense
        editAmount = expense.amount.toString()
    }
    fun saveEdit(){
        val expense = editExpense ?: return
        val newAmount = editAmount.toIntOrNull()?: return
        if (newAmount <= 0) return
        expenses.value = expenses.value.map {
            if (it.id == expense.id) it.copy(amount = newAmount) else it
        }
        editExpense = null
        editAmount = ""
    }
    fun cancelEdit(){
        editExpense = null
        editAmount = ""
    }
}