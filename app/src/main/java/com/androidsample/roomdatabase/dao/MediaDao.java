package com.androidsample.roomdatabase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.androidsample.roomdatabase.tables.MediaEntity;

@Dao
public abstract class MediaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(MediaEntity mediaBean);
}
