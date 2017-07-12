package persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface AbstractDao<T> {

    public int add(T t);

    public void delete(T t);

    public int update(T t);

    public Optional<T> find(int id);

    public List<T> findAll();
}
