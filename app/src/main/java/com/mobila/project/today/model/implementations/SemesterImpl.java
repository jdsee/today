package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.OrganizerDataRep;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;

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
