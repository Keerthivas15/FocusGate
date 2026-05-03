package com.focusgate.di

import android.content.Context
import androidx.room.Room
import com.focusgate.data.db.BlockedAppDao
import com.focusgate.data.db.CreditDao
import com.focusgate.data.db.FocusGateDatabase
import com.focusgate.math.ProblemRegistry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): FocusGateDatabase =
        Room.databaseBuilder(ctx, FocusGateDatabase::class.java, "focusgate.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideBlockedAppDao(db: FocusGateDatabase): BlockedAppDao = db.blockedAppDao()

    @Provides
    fun provideCreditDao(db: FocusGateDatabase): CreditDao = db.creditDao()
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProblemRegistry(): ProblemRegistry = ProblemRegistry()
}
