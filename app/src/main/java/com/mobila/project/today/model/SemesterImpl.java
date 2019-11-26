package com.mobila.project.today.model;

import com.mobila.project.today.dataModelAccess.OrganizerDataRep;

import java.util.List;

class SemesterImpl implements Semester {
    private static OrganizerDataRep dataRep;
    private final int id;

    static {
        //dataRep = OrganizerDataRep.getInstance();
    }

    public SemesterImpl(int id) {
        this.id = id;
    }

    @Override
    public List<Course> getCourses() {
        return null;
    }

    @Override
    public int getID() {
        return this.id;
    }
}
