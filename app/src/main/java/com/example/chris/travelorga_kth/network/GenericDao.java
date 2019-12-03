package com.example.chris.travelorga_kth.network;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {

    /** Save your object */
    void create(T newInstance);
//    PK create(T newInstance);

    /** Retrieve the object thanks to its primary key */
    void retrieve(PK id);
//    T retrieve(PK id);

    /** Update an existing object */
    void update(T instance);

    /** Remove an object */
    void delete(T instance);

    /** Retrieve all */
    // TODO
    // void retrieveAll();
}
