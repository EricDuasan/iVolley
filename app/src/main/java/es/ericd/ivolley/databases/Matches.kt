package es.ericd.ivolley.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Matches(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "team") val team: String,
    @ColumnInfo(name = "opponent") val opponent: String,
    @ColumnInfo(name = "opponentFlag") val opponentFlag: String,
    @ColumnInfo(name = "result") val result: String,
)