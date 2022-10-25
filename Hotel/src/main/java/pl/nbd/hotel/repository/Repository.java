package pl.nbd.hotel.repository;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    T findById(String id);
    T save(T object);
    List<T> find(Predicate<T> predicate);
    List<T> findAll();
    String getReport();
    int getSize();
    void remove(T object);
}
