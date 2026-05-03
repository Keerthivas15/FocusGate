package com.focusgate.data.repository

import com.focusgate.data.db.BlockedAppDao
import com.focusgate.data.db.BlockedAppEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockedAppRepository @Inject constructor(
    private val dao: BlockedAppDao
) {
    val blockedPackages: Flow<List<String>> = dao.observeAll()

    suspend fun getBlockedPackages(): List<String> = dao.getAll()

    suspend fun toggle(packageName: String) {
        if (dao.exists(packageName) > 0) {
            dao.delete(packageName)
        } else {
            dao.insert(BlockedAppEntity(packageName))
        }
    }

    suspend fun isBlocked(packageName: String): Boolean =
        dao.exists(packageName) > 0
}
