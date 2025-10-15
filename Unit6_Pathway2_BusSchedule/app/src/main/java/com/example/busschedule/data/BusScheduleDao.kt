package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BusScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(busSchedule: BusSchedule)

    @Update
    suspend fun update(busSchedule: BusSchedule)

    @Delete
    suspend fun delete(busSchedule: BusSchedule)

    @Query("SELECT * from bus_schedules WHERE id = :id")
    fun getItem(id: Int): Flow<BusSchedule>

    @Query("SELECT * from bus_schedules ORDER BY stopName ASC")
    fun getAll(): Flow<List<BusSchedule>>

    @Query(value = "SELECT * from bus_schedules WHERE stopName = :stopName")
    fun getByStopName(stopName: String): Flow<List<BusSchedule>>
}