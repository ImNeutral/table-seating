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

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.trackmysleepquality.database.Table

@Dao
interface TableDao {

    @Insert
    fun insert(table: Table)

    @Update
    fun update(table: Table)

    @Query("SELECT * from room_table WHERE id = :key")
    fun get(key: Long): Table?

    @Query("DELETE FROM room_table")
    fun clear()

    @Query("SELECT * FROM room_table ORDER BY id DESC")
    fun getAllTables(): LiveData<List<Table>>
}