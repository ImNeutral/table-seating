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

    /*fun initTableSizes() {
        uiScope.launch {
            initTableSizesNow()
        }
    }

    suspend fun initTableSizesNow() {
        withContext(Dispatchers.IO) {
            //var tableSizesLocal = List<LiveData<Int>>
            for(tableIdLocal in 1..4) {
                //database.getCustomerCountOnTable(1).toLong()
                tableSizes.add(database.getCustomerCountOnTable(1))
            }
            //tableSizes = tableSizesLocal
        }
    }*/
//
//    suspend fun initTableSizesNow() {
//        withContext(Dispatchers.IO) {
//            for (innerTableId in 0..4) {
//                var tableSize = database.getCustomerCountOnTable(innerTableId.toLong())
//                var mapCount = mapOf<>()
//            }
//        }
//    }
}

