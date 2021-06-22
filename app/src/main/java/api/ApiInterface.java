package api;

import java.util.List;

/**
 * An interface of Api
 * @param <T> an Object type
 */
public interface ApiInterface<T> {

    List<T> getAll();
    T get(String id);
    T add(T object);
    boolean patch(String id, T object);
    boolean remove(String id);

}
