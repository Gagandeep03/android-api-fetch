package com.androidsample.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.androidsample.roomdatabase.dao.MediaDao;
import com.androidsample.roomdatabase.dao.MediaMetaDao;
import com.androidsample.roomdatabase.dao.ResultDao;
import com.androidsample.roomdatabase.tables.MediaEntity;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.roomdatabase.tables.ResultsEntity;


@Database(entities = {ResultsEntity.class, MediaEntity.class, MediaMetadataEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ResultDao resultDao();

    public abstract MediaDao mediaDao();

    public abstract MediaMetaDao mediaMetaDao();


}
