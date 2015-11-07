package com.nikolay.r3s.data.repositories;

import android.util.Log;

import com.nikolay.r3s.constants.RepositoryTypes;
import com.nikolay.r3s.data.context.DataContext;
import com.nikolay.r3s.models.IModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

public class GenericRepository<T extends IModel> implements IRepository<T> {
    private DataContext context;
    private Hashtable set;

    public GenericRepository(RepositoryTypes type) {
        this.context = DataContext.getInstance();
        this.set = this.context.getSet(type);
    }

    @Override
    public T getById(int id) {
        return (T)set.get(id);
    }

    @Override
    public ArrayList<T> getAll() {
        ArrayList<T> result = new ArrayList<T>(this.set.values());
        return result;
    }

    @Override
    public void create(T newObject) {
        UUID randomId =  UUID.randomUUID();
        newObject.setId(randomId.toString());
        this.set.put(newObject.getId(), newObject);
    }

    @Override
    public void update(T updatedObject) {
        this.set.put(updatedObject.getId(), updatedObject);
    }

    @Override
    public void delete(T deletedObject) {
        this.set.remove(deletedObject);
    }

    @Override
    public ArrayList<T> query(ISpecification specification) {
        ArrayList<T> elements = new ArrayList<T>(this.set.values());
        ArrayList<T> filterElements = new ArrayList<T>();

        for(Object element: elements) {
            if(specification.specified((IModel) element)) {
                filterElements.add((T) element);
            }
        }

        return filterElements;
    }
}
