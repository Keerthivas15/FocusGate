package com.focusgate.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// ─── Blocked App ──────────────────────────────────────────────────────────────
@Entity(tableName = "blocked_apps")
data class BlockedAppEntity(
    @PrimaryKey val packageName: String
)

@Dao
interface BlockedAppDao {
    @Query("SELECT packageName FROM blocked_apps")
    fun observeAll(): Flow<List<String>>

    @Query("SELECT packageName FROM blocked_apps")
    suspend fun getAll(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(app: BlockedAppEntity)

    @Query("DELETE FROM blocked_apps WHERE packageName = :pkg")
    suspend fun delete(pkg: String)

    @Query("SELECT COUNT(*) FROM blocked_apps WHERE packageName = :pkg")
    suspend fun exists(pkg: String): Int
}

// ─── Credit Entry ─────────────────────────────────────────────────────────────
@Entity(tableName = "credits")
data class CreditEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val earnedAt: Long,
    val expiresAt: Long,
    val used: Boolean = false
)

@Dao
interface CreditDao {
    @Query("SELECT * FROM credits WHERE used = 0 AND expiresAt > :now ORDER BY earnedAt ASC")
    suspend fun validCredits(now: Long): List<CreditEntity>

    @Query("SELECT COUNT(*) FROM credits WHERE used = 0 AND expiresAt > :now")
    fun observeCount(now: Long): Flow<Int>

    @Insert
    suspend fun insert(credit: CreditEntity): Long

    @Query("UPDATE credits SET used = 1 WHERE id = :id")
    suspend fun markUsed(id: Long)

    @Query("DELETE FROM credits WHERE expiresAt <= :now")
    suspend fun purgeExpired(now: Long)
}
