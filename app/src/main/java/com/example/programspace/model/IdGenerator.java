package com.example.programspace.model;

public class IdGenerator {
    int userNextId;
    int projectNextId;
    int techSkillNextId;

    public static final IdGenerator instance =new IdGenerator();

    private IdGenerator(){
        userNextId= 0 ;
        projectNextId = 0;
        techSkillNextId =0;
    }

    public int getUserNextId() {
        userNextId+=1;
        return userNextId;
    }

    public int getProjectNextId() {
        projectNextId+=1;
        return projectNextId;
    }

    public int getTechSkillNextId() {
        techSkillNextId+=1;
        return techSkillNextId;
    }
}
