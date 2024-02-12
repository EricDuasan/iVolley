package es.ericd.ivolley.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking")
data class Ranking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "flag") val flag: String,
    @ColumnInfo(name = "score") val score: Int,
)