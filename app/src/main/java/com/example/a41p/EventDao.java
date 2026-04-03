package com.example.a41p;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
@Dao
public interface EventDao {
    @Insert
    void insert (Event event);

    @Query("SELECT * FROM Event ORDER BY dateTime ASC")
    List<Event> getAllEvents();

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);
}
