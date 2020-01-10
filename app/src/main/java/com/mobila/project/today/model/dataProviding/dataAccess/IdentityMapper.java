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

    /**
     * Adds the specified course to the corresponding entry if it exists.
     * Nothing happens if there is no entry assigned to the specified key.
     * <p>
     * Nothing happens if the element already exists in the entry related to the specified key.
     * <p>
     * Note that no entry will be created if there was'nt one existing before calling this method.
     * In this case, call the add()-method and pass a list containing the specified element if
     * you want to create an entry for it.
     *
     * @param key     the key related to element
     * @param element the element that is to be added
     */
    public void addElement(Identifiable key, T element) {
        List<T> entries = this.map.get(key.getID());
        if (entries != null && !entries.contains(element)) {
            entries.add(element);
//            this.map.put(key.getID(), entries);
        }
    }

    /**
     * Overwrites the entry related to the specified key with the the specified values.
     * Nothing happens if no entry exists for the specified key.
     *
     * @param key    the key related to the values
     * @param values the values to be added
     */
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

    /**
     * Removes the specified element in the corresponding entry if it exists.
     * Nothing happens if there is no entry assigned to the specified key.
     *
     * @param key     the key related to element
     * @param element the element that is to be removed
     */
    public void removeElement(Identifiable key, T element) {
        List<T> entries = this.map.get(key.getID());
        if (entries != null) {
            entries.remove(element);
//            this.map.put(key.getID(), entries);
        }
    }
}