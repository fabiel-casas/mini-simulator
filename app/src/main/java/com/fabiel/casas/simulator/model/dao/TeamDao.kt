package com.fabiel.casas.simulator.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fabiel.casas.simulator.model.table.Team

/**
 * Mingle Sport Tech
 * Created on 06/05/2023.
 */
@Dao
interface TeamDao {
    @Insert
    suspend fun insert(team: Team)

    @Query("DELETE FROM Team")
    suspend fun deleteAll()

    @Query("SELECT * FROM Team")
    suspend fun getTeams(): List<Team>

    @Query("SELECT * FROM Team WHERE id = :teamId")
    suspend fun getTeam(teamId: String): Team
}