package ru.ath.athautowatcher.data;

import androidx.annotation.Nullable;
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

    @Query(
            "SELECT * FROM transport " +
                    "WHERE (:invnom IS NULL OR atinvnom LIKE :invnom) " +
                    "AND (:autocol IS NULL OR  atautocol = :autocol) " +
                    "AND (:department IS NULL OR  atdepartment = :department)"
    )
    LiveData<List<Transport>> getTransportByFilter(@Nullable String invnom, @Nullable String autocol, @Nullable String department);

    @Query("SELECT * FROM transport WHERE id == :id")
    Transport getTransportById(int id);

    @Query("DELETE FROM transport")
    void DeleteAllTransport();

    @Delete
    void DeleteTransport(Transport transport);

    @Insert
    void insertTransport(Transport transport);

    // автоколонны
    @Query("SELECT DISTINCT(t.atautocol) FROM transport t ORDER BY atautocol")
    List<String> getAllAutocols();

    // подразделения в автоколонне
    @Query("SELECT DISTINCT(t.atdepartment) FROM transport t WHERE t.atautocol = :autocol ORDER BY atdepartment")
    List<String> getDepartments(String autocol);


//    @Update
//    void updateTransport(Transport transport);
}
