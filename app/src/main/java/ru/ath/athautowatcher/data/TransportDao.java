package ru.ath.athautowatcher.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransportDao {
    @Query("SELECT * FROM transport")
    LiveData<List<Transport>> getAllTransports();

    @Query("SELECT * FROM transport WHERE id == :id")
    Transport getTransportById(int id);

    @Query("DELETE FROM transport")
    void DeleteAllTransport();

    @Delete
    void DeleteTransport(Transport transport);

    @Insert
    void insertTransport(Transport transport);

//    @Update
//    void updateTransport(Transport transport);
}
