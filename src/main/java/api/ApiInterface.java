package api;

import java.util.List;

/**
 *
 * @param <T>
 */
public interface ApiInterface<T> {

    List<T> getAll();
    T get(String id);
    T add(T object);
    boolean patch(String id, T object);
    boolean remove(String id);

}
