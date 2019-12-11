package com.mobila.project.today.model.dataProviding.dataAccess;

import androidx.annotation.NonNull;

import com.mobila.project.today.model.Identifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class IdentityMapper<T extends Identifiable> {
    private final Map<Integer, List<T>> map = new HashMap<>();

    public List<T> get(@NonNull Identifiable key) {
        Objects.requireNonNull(key);
        return this.map.get(key.getID());
    }

    public void add(@NonNull Identifiable key,@NonNull List<T> values) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(values);
        int id = key.getID();
        if (this.map.containsKey(id))
            this.map.put(id, values);
    }

    public void overwrite(@NonNull Identifiable key, @NonNull List<T> values) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(values);
        this.map.put(key.getID(), values);
    }

    public void remove(T obj) {
        this.map.remove(obj.getID());
    }
}