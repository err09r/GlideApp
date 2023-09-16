package com.apsl.glideapp.core.database.dao

import androidx.room.Query

interface BaseDao {

    @Query("SELECT (SELECT COUNT(*) FROM rides) == 0")
    fun isTableEmpty(): Boolean
}
