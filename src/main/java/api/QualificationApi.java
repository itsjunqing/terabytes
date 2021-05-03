package api;

import stream.Qualification;

import java.util.List;

/**
 * A class that performs API communication on Qualification.
 */
public class QualificationApi extends BasicApi<Qualification> {

    private final String QUALIFICATION_ENDPOINT = "/qualification";

    @Override
    public List<Qualification> getAll() {
        return getAllObjects(QUALIFICATION_ENDPOINT, Qualification[].class);
    }

    @Override
    public Qualification get(String id) {
        return getObject(QUALIFICATION_ENDPOINT + "/" + id, Qualification.class);
    }

    @Override
    public Qualification add(Qualification object) {
        return postObject(QUALIFICATION_ENDPOINT, object, Qualification.class);
    }

    @Override
    public boolean patch(String id, Qualification object) {
        return patchObject(QUALIFICATION_ENDPOINT, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(QUALIFICATION_ENDPOINT + "/" + id);
    }
}
