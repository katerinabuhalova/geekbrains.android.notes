package com.example.notes.date;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NoteMapping {

    public static class Fields {
        public final static String NAME = "nameNote";
        public final static String DATE = "date";
        public final static String DESCRIPTION = "description";

    }

    public static Note toNote(String id, Map<String, Object> doc) {
        Note note = new Note((String) doc.get(Fields.NAME), (String) doc.get(Fields.DATE), (String) doc.get(Fields.DESCRIPTION));
        note.setId(id);
        return note;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, note.getNameNote());
        answer.put(Fields.DATE, note.getDate());
        answer.put(Fields.DESCRIPTION, note.getDescriptionNote());
        return answer;
    }
}

