/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tableseatingtest1

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.Table
import com.example.android.trackmysleepquality.database.TableSeatingDatabase
import com.example.tableseatingtest1.database.Customer
import com.example.tableseatingtest1.database.dao.CustomerDao
import com.example.tableseatingtest1.database.dao.TableDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class TableSeatingDatabaseTest {

    private lateinit var tableDao: TableDao
    private lateinit var customerDao: CustomerDao
    private lateinit var db: TableSeatingDatabase

    init {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TableSeatingDatabase::class.java)
            // Allowing main thread queries, just for testing.
            //.allowMainThreadQueries()
            .build()
        tableDao = db.tableDao
        customerDao = db.customerDao
    }

    @Before
    fun createDb() {
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertTable() {
        val table = Table(1, "Table #1", 5)
        tableDao.insert(table)
        tableDao.get(1)

        val tableChecker = tableDao.get(1)

        assertEquals(tableChecker?.maxCustomer, 5)
    }

    @Test
    fun insertTableAndCustomer() {
        val table = Table(1, "Table #1", 5)
        tableDao.insert(table)
        tableDao.get(1)

        val customer = Customer(1, 1, "Christian Garillo")
        customerDao.insert(customer)

        val customerChecker = customerDao.get(1)

        val customersFromTable = customerDao.getAllCustomersFromTable(1)
        /*    customersFromTable.forEach{
                Log.i("TableSeatingDatabaseTest", "Name: ${it.customerName}")
            }*/
        assertEquals(customerChecker?.customerName, "Christian Garillo")
    }
}


