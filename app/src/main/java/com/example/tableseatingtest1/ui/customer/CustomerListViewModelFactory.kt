package com.example.tableseatingtest1.ui.customer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tableseatingtest1.database.dao.CustomerDao

class CustomerListViewModelFactory(
    private val dataSource: CustomerDao,
    private val application: Application ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerListViewModel::class.java)) {
            return CustomerListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}