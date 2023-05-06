package com.fabiel.casas.simulator.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabiel.casas.simulator.model.table.Match
import kotlinx.coroutines.flow.Flow

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Query("DELETE FROM `Match`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `Match`")
    fun getMatchesFlow(): Flow<List<Match>>

    @Query("SELECT * FROM `Match` WHERE roundId=:roundId")
    fun getMatchesByRound(roundId: Int): Flow<List<Match>>
}