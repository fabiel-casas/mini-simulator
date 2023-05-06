package com.fabiel.casas.simulator.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fabiel.casas.simulator.model.dao.MatchDao
import com.fabiel.casas.simulator.model.dao.TeamDao
import com.fabiel.casas.simulator.model.table.Match
import com.fabiel.casas.simulator.model.table.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Mingle Sport Tech
 * Created on 05/05/2023.
 */
@Database(
    entities = [Match::class, Team::class],
    version = 1,
    exportSchema = false
)
abstract class MiniSimulatorDatabase : RoomDatabase() {

    abstract fun getMatch(): MatchDao
    abstract fun getTeam(): TeamDao

    private class DefaultMiniSimulatorDatabase(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val teamDao = database.getTeam()
                    teamDao.deleteAll()
                    teamDao.insert(
                        Team(
                            name = "PSV",
                            logo = "https://upload.wikimedia.org/wikinews/en/f/fe/PSV_logo.png",
                            attack = 6,
                            defense = 30,
                            possession = 50,
                            recovery = 80
                        )
                    )
                    teamDao.insert(
                        Team(
                            name = "AJAX",
                            logo = "https://upload.wikimedia.org/wikipedia/en/thumb/7/79/Ajax_Amsterdam.svg/640px-Ajax_Amsterdam.svg.png",
                            attack = 9,
                            defense = 40,
                            possession = 80,
                            recovery = 85
                        )
                    )
                    teamDao.insert(
                        Team(
                            name = "Twente",
                            logo = "https://upload.wikimedia.org/wikipedia/en/thumb/e/e3/FC_Twente.svg/1200px-FC_Twente.svg.png",
                            attack = 7,
                            defense = 35,
                            possession = 60,
                            recovery = 50
                        )
                    )
                    teamDao.insert(
                        Team(
                            name = "Sparta Rotterdam",
                            logo = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9f/Sparta_Rotterdam_logo.svg/1200px-Sparta_Rotterdam_logo.svg.png",
                            attack = 4,
                            defense = 45,
                            possession = 50,
                            recovery = 90
                        )
                    )
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: MiniSimulatorDatabase? = null

        fun buildDatabase(
            context: Context,
            scope: CoroutineScope,
        ): MiniSimulatorDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    MiniSimulatorDatabase::class.java,
                    "mini_simulator.db"
                )
                    .addCallback(DefaultMiniSimulatorDatabase(scope = scope))
                    .build()
                INSTANCE!!
            }
        }
    }
}