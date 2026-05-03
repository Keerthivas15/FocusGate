package com.focusgate.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BlockedAppEntity::class, CreditEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FocusGateDatabase : RoomDatabase() {
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun creditDao(): CreditDao
}
