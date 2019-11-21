package com.mobila.project.today.modelMock;

import java.util.List;

public class Section {
    private List<Note> notes;

    public Section(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
