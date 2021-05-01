package api;

import stream.Competency;

import java.util.List;

public class CompetencyApi extends BasicApi<Competency> {

    private final String COMPETENCY_ENDPOINT = "/competency";

    @Override
    public List<Competency> getAll() {
        return getAllObjects(COMPETENCY_ENDPOINT, Competency[].class);
    }

    @Override
    public Competency get(String id) {
        return getObject(COMPETENCY_ENDPOINT + "/" + id, Competency.class);
    }

    @Override
    public Competency add(Competency object) {
        return postObject(COMPETENCY_ENDPOINT, object, Competency.class);
    }

    @Override
    public boolean patch(String id, Competency object) {
        return patchObject(COMPETENCY_ENDPOINT, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(COMPETENCY_ENDPOINT + "/" + id);
    }

}
