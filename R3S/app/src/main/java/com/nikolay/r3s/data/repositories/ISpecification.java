package com.nikolay.r3s.data.repositories;

import com.nikolay.r3s.models.IModel;

public interface ISpecification<T extends IModel> {
    boolean specified(T account);
}
