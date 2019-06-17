package com.androidsample.roomdatabase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.androidsample.roomdatabase.tables.MediaMetadataEntity;

import java.util.List;

@Dao
public abstract class MediaMetaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAll(List<MediaMetadataEntity> entries);
}
