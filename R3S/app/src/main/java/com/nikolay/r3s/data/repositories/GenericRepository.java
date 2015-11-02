package com.nikolay.r3s.data.repositories;

import android.util.Log;

import com.nikolay.r3s.constants.RepositoryTypes;
import com.nikolay.r3s.data.context.DataContext;
import com.nikolay.r3s.models.IModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

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
    public <TSpecification extends ISpecification> List query(TSpecification specification) {
        ArrayList<T> elements = new ArrayList<T>();
        for(Object element: this.set.values()) {
            T current = (T)this.set.values();

            if(specification.specified(current)) {
                elements.add(current);
            }
        }

        return elements;
    }
}
