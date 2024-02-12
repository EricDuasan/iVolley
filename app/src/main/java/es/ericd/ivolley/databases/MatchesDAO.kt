package es.ericd.ivolley.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchesDAO {
    @Query("SELECT * FROM matches WHERE team = :team") suspend fun getMatchesByCountry(team: String): List<Matches>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertMatch(matches: Matches): Long

    @Query("DELETE FROM matches WHERE team = :team")
    suspend fun deleteCar(team: String)

}