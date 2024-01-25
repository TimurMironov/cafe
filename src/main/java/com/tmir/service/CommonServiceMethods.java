package com.tmir.service;

import java.util.List;

public interface CommonServiceMethods<T> {

    List<T> list();

    T getById(Integer id);

    T save(T object);

    void delete(Integer id);
}
