package es.ericd.ivolley.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RankingDAO {
    @Query("SELECT * FROM ranking") suspend fun getRanking(): List<Ranking>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertRanking(databaseRanking: Ranking): Long

    @Query("DELETE FROM ranking")
    suspend fun deleteAll()

}