package es.ericd.ivolley.databases

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(
    entities = [Ranking::class, Matches::class],
    version = 2
)

abstract class RankingDatabase: RoomDatabase() {
    abstract fun rankingDao(): RankingDAO
    abstract fun matchesDao(): MatchesDAO

    companion object {
        private const val DATABASE_NAME = "volleyball_db"

        private var instance: RankingDatabase? = null

        fun getInstance(context: Context): RankingDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, RankingDatabase::class.java, DATABASE_NAME).build()
                }
            }
            return instance!!
        }

    }
}