package api;

import stream.Subject;

import java.util.List;

public class SubjectApi extends BasicApi<Subject> implements ApiInterface<Subject> {

    private final String SUBJECT_ENDPOINT = "/subject";

    @Override
    public List<Subject> getAll() {
        return getAllObjects(SUBJECT_ENDPOINT, Subject[].class);
    }

    @Override
    public Subject get(String id) {
        return getObject(SUBJECT_ENDPOINT + "/" + id, Subject.class);
    }

    @Override
    public Subject add(Subject object) {
        return postObject(SUBJECT_ENDPOINT, object, Subject.class);
    }

    @Override
    public boolean patch(String id, Subject object) {
        return patchObject(SUBJECT_ENDPOINT, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(SUBJECT_ENDPOINT + "/" + id);
    }

    /*
    public List<Subject> getAllSubjects() {
        return getAll();
    }
    */
}
