package com.arielelbert.vpassignmentweek5.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arielelbert.vpassignmentweek5.model.Expense
import com.arielelbert.vpassignmentweek5.viewmodel.ExpenseViewModel


@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = viewModel()){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Text(
                text = "Expenses",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Total Expense : ${viewModel.Rupiah(viewModel.totalExpense)}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.expenseName,
                onValueChange = { viewModel.expenseName = it },
                label = { Text("Expense Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.expenseAmount,
                    onValueChange = {
                        viewModel.expenseAmount = it
                        if (viewModel.errorMessage != null) viewModel.errorMessage = null
                    },
                    label = { Text("Amount of Expense") },
                    isError = viewModel.errorMessage != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(8.dp))

                IconButton(
                    onClick = { viewModel.addExpense() },
                    modifier = Modifier.size(56.dp)
                        .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
            if (viewModel.errorMessage != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = viewModel.errorMessage!!,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.expenses.value) { expense ->
                    ExpenseItemCard(
                        expense = expense,
                        formattedAmount = viewModel.Rupiah(expense.amount),
                        onDelete = { viewModel.deleteExpense(expense) },
                        onEdit = { viewModel.startEdit(expense) }
                    )
                }
            }
        }
                if (viewModel.editExpense != null){
                    AlertDialog(
                        onDismissRequest = {viewModel.cancelEdit()},
                        title = {Text("Edit Amount: ${viewModel.editExpense?.name}")},
                        text = {
                            OutlinedTextField(
                                value = viewModel.editAmount,
                                onValueChange = {viewModel.editAmount = it},
                                label = {Text("New Amount")},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {viewModel.saveEdit()}) {
                                Text("Save")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {viewModel.cancelEdit()}) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }

@Composable
fun ExpenseItemCard(
    expense: Expense,
    formattedAmount: String,
    onDelete: () -> Unit,
    onEdit: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(contentColor = Color(0xFFE8EEF5)),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    text = "Name : ${expense.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formattedAmount,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "Edit",
                    tint = Color(0xFFCCA200)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseAppPreview(){
    ExpenseScreen()
}