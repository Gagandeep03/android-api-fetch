package com.androidsample.roomdatabase.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Transaction;

import com.androidsample.beans.ResultLiveBean;
import com.androidsample.roomdatabase.tables.ResultsEntity;


@Dao
public abstract class ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ResultsEntity entryBean);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT re.id,re.title,re.time,re.byline,re.publishedDate,re.section,re.abstractX,re.views,re.source" +
            " FROM ResultsEntity re inner join MediaEntity me inner join" +
            " MediaMetadataEntity mme On re.id==me.resultId And me.mediaId==mme.mediaId" +
            " group by re.id  order by re.publishedDate Desc ")
    public abstract DataSource.Factory<Integer, ResultLiveBean> entriesByDistinctViews();

}
