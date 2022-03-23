package com.example.programspace.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TechSkill {

    @PrimaryKey
    @NonNull
    int id;
    String skill_name;

    public TechSkill(String skill_name) {
        this.skill_name = skill_name;
    }
}
