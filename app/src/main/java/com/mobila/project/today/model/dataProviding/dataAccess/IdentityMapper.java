package com.mobila.project.today.model.dataProviding.dataAccess;

import androidx.annotation.NonNull;

import com.mobila.project.today.model.Identifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class IdentityMapper<T extends Identifiable> {
    private final Map<String, List<T>> map = new HashMap<>();

    public List<T> get(@NonNull Identifiable key) {
        Objects.requireNonNull(key);
        return this.map.get(key.getID());
    }

    public void add(@NonNull Identifiable key, @NonNull List<T> values) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(values);
        String id = key.getID();
        if (this.map.containsKey(id))
            throw new KeyAlreadyAssignedException();
        this.map.put(id, values);
    }

    public void overwrite(@NonNull Identifiable key, @NonNull List<T> values) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(values);
        this.map.put(key.getID(), values);
    }

    /**
     * Removes the entry assigned to the specified key.
     * Nothing happens if there is no value assigned to the specified key or it is null.
     *
     * @param key the key whose entry should be removed
     */
    public void remove(Identifiable key) {
        this.map.remove(key.getID());
    }
}