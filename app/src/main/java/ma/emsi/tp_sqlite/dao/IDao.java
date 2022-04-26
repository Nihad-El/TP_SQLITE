package ma.emsi.tp_sqlite.dao;

import java.util.List;


public interface IDao<T> {

    void add(T o);

    T findById(int id);

    List<T> findAll();

    void update(T o);

    void delete(T o);
}
