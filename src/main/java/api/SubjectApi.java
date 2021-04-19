package api;

import stream.Subject;

import java.util.List;

public class SubjectApi extends BasicApi<Subject> {

    private final String SUBJECT_ENDPOINT = "/subject";

    public List<Subject> getAllSubjects() {
        return getAllObjects(SUBJECT_ENDPOINT, Subject[].class);
    }

    public Subject getSubject(String id) {
        String endpoint = SUBJECT_ENDPOINT + "/" + id;
        return getObject(endpoint, Subject.class);
    }

    public Subject addSubject(Subject subject) {
        return postObject(SUBJECT_ENDPOINT, subject, Subject.class);
    }

    public boolean patchSubject(Subject subject) {
        return patchObject(SUBJECT_ENDPOINT, subject);
    }

    public boolean removeSubject(String id) {
        return deleteObject(SUBJECT_ENDPOINT + "/" + id);
    }

}
