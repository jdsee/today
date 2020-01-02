package today.model.dataProviding.dataAccess;

import android.text.Spannable;

import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Identifiable;
import today.model.Lecture;
import today.model.NoteReference;

import java.util.List;

public interface NoteDataAccess {

    Lecture getLecture(Identifiable note) throws DataKeyNotFoundException;

    String getTitle(Identifiable note) throws DataKeyNotFoundException;

    void setTitle(Identifiable note, String title) throws DataKeyNotFoundException;

    Spannable getContent(Identifiable note) throws DataKeyNotFoundException;

    void setContent(Identifiable note, Spannable content) throws DataKeyNotFoundException;

    List<NoteReference> getReferences(Identifiable note) throws DataKeyNotFoundException;

    void addReference(Identifiable note, Identifiable reference, int row) throws DataKeyNotFoundException;
}
