package com.example.programspace.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TechSkillDao {

    @Query("select * from TechSkill")
    List<TechSkill> getAll();

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insertAll(TechSkill... techSkills);

    @Delete
    void delete(TechSkill techSkill);
}
