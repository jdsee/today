package com.mobila.project.today.dataProviding.dataAccess;

import androidx.annotation.NonNull;

import com.mobila.project.today.model.Identifiable;

import java.util.HashMap;
import java.util.Map;

class IdentityMapper<T extends Identifiable> {
    private final Map<Integer, T> map = new HashMap<>();

    public Identifiable get(int id) {
        return this.map.get(id);
    }

    public void add(@NonNull T obj) {
        int key = obj.getID();
        if (this.map.containsKey(key))
            this.map.put(key, obj);
    }

    public void overwrite(@NonNull T obj) {
        this.map.put(obj.getID(), obj);
    }

    public void remove(T obj) {
        this.map.remove(obj.getID());
    }

    public void remove(int key) {
        this.map.remove(key);
    }
}
