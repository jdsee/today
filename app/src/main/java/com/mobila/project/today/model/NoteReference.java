package com.mobila.project.today.model;

public interface NoteReference extends Identifiable {
    Note getReference();

    Note getSource();

    int getRow();
}
