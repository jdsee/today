package com.mobila.project.today.activities;

import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.LectureImpl;
import com.mobila.project.today.model.implementations.SectionImpl;
import com.mobila.project.today.model.implementations.TaskImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleCollection {
    public static List<Task> getExampleTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new TaskImpl(8, "some Content", new java.util.Date()));
        tasks.add(new TaskImpl(9, "some Random", new java.util.Date()));
        tasks.add(new TaskImpl(10, "some mor Random", new Date()));
        return tasks;
    }

    public static List<Section> getExampleSections() {
        List<Section> sections = new ArrayList<>();
        sections.add(new SectionImpl(123, "Vorlesung", "Prof. Schwotzer"));
        sections.add(new SectionImpl(321, "Übung", "Prof. Schwotzer"));
        return sections;
    }

    public static List<Lecture> getExampleLectures() {
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new LectureImpl(456, 1, new Date(), "WH C666"));
        lectures.add(new LectureImpl(4536, 2, new Date(), "WH C445"));
        lectures.add(new LectureImpl(4526, 3, new Date(), "WH C624"));
        return lectures;
    }
}
