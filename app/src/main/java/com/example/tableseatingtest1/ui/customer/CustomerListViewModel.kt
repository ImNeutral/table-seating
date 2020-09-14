package com.example.tableseatingtest1.ui.customer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tableseatingtest1.database.Customer
import com.example.tableseatingtest1.database.dao.CustomerDao
import kotlinx.coroutines.*

class CustomerListViewModel(
    val database: CustomerDao,
    application: Application ) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //lateinit var tableSizes: MutableList<LiveData<Int>>
    lateinit var allCustomers: LiveData<List<Customer>>
    lateinit var customersOnTable: LiveData<List<Customer>>
    var customerName: String = ""
    var tableId: Long = 0L
    var noCustomersAllowed = 5

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun createCustomer() {
        uiScope.launch {
            createInBackground()
        }
    }

    suspend fun createInBackground() {
        withContext(Dispatchers.IO) {
            var customer: Customer = Customer(0L, tableId, customerName)
            Log.i("CustomerListViewModel", customerName)
            database.insert(customer)
        }
    }

    fun clearAllCustomers() {
        uiScope.launch {
            clearAllCustomersInBackground()
        }
    }

    suspend fun clearAllCustomersInBackground() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun clearCustomers() {
        uiScope.launch {
            clearCustomersInBackground()
        }
    }

    suspend fun clearCustomersInBackground() {
        withContext(Dispatchers.IO) {
            database.clearByTableNo(tableId)
        }
    }

    fun initCustomersOnTable() {
        customersOnTable = database.getAllCustomersFromTable(tableId)
    }

    fun initAllCustomers() {
        allCustomers = database.getAllCustomers()
    }
}

