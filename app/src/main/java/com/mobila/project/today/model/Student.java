package com.mobila.project.today.model;

import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;

import java.util.List;

public class Student {
    private RootDataAccess rootDataAccess;
//    private List<Semester> semesters;

    public Student() {
        this.rootDataAccess = OrganizerDataProvider.getInstance().getRootDataAccess();
    }

    public List<Semester> getAllSemesters() {
        return this.rootDataAccess.getAllSemesters();
//        if (this.semesters == null) {
//            this.semesters = this.rootDataAccess.getAllSemesters();
//        }
//        return this.semesters;
    }

    public void addSemester(Semester semester){
        this.rootDataAccess.addSemester(semester);
    }

    public void removeSemester(Semester semester){
        for (Course course : semester.getCourses()){
            semester.removeCourse(course);
        }
        this.rootDataAccess.removeSemester(semester);
    }

    public List<Task> getAllTasks(){
        return this.rootDataAccess.getAllTasks();
    }
}