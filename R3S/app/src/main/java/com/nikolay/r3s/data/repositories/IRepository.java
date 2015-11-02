package com.nikolay.r3s.data.repositories;

import com.nikolay.r3s.models.IModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IRepository<T extends IModel> {

    T getById(int id);
    ArrayList<T> getAll();
    void create(T newObject);
    void update(T updatedObject);
    void delete(T deletedObject);

    <TSpecification extends ISpecification> List query(TSpecification specification);
}
