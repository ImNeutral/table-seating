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

package com.example.tableseatingtest1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tableseatingtest1.database.Customer

@Dao
interface CustomerDao {

    @Insert
    fun insert(night: Customer)

    @Update
    fun update(night: Customer)

    @Query("SELECT * from customer WHERE id = :key")
    fun get(key: Long): Customer?

    @Query("DELETE FROM customer")
    fun clear()

    @Query("SELECT * FROM customer ORDER BY id DESC")
    fun getAllCustomers(): List<Customer>

    @Query("SELECT * FROM customer WHERE table_id=:tableId")
    fun getAllCustomersFromTable(tableId: Long): List<Customer>
}