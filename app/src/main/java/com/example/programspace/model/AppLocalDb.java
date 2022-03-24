package com.example.programspace.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.programspace.MyApplication;

@Database(entities = {User.class,Project.class}, version = 4)
@TypeConverters(Converters.class)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProjectDao projectDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
