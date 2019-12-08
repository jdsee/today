package com.mobila.project.today.activities;

import com.mobila.project.today.model.Task;
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
}
