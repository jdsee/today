package com.mobila.project.today.modelMock;

import java.util.List;

public class SectionMock {
    private List<NoteMock> notes;

    public SectionMock(List<NoteMock> notes) {
        this.notes = notes;
    }

    public List<NoteMock> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteMock> notes) {
        this.notes = notes;
    }
}
