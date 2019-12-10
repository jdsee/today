package com.mobila.project.today.model;

public class NoteReference implements Identifiable {
    private final int ID;
    private Note reference;
    private Note source;
    private int row;

    public NoteReference(int id, Note reference, Note source, int row) {
        this.ID = id;
        this.reference = reference;
        this.source = source;
        this.row = row;
    }

    public NoteReference(Note reference, Note source, int row) {
        this(
                1234, //TODO replace with DataKeyGenerator
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
    public int getID() {
        return this.ID;
    }
}
