package com.mobila.project.today.model;

import java.util.UUID;

public class NoteReference implements Identifiable {
    private final String ID;
    private Note reference;
    private Note source;
    private int row;

    public NoteReference(String id, Note reference, Note source, int row) {
        this.ID = id;
        this.reference = reference;
        this.source = source;
        this.row = row;
    }

    public NoteReference(Note reference, Note source, int row) {
        this(
                UUID.randomUUID().toString(),
                reference, source, row
        );
    }

    Note getReference() {
        return this.reference;
    }

    Note getSource() {
        return this.source;
    }

    int getRow() {
        return this.row;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
