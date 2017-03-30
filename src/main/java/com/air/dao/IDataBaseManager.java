package com.air.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linux on 2017年03月29日.
 * Time 22:36
 */
public interface IDataBaseManager<T> {
    <ID extends Serializable> T get(Class<T> tClass, ID id);

    <ID extends Serializable> ID create(T t);

    int batchSave(final List<T> array);


    int batchDelete(String jpaSql, String[] ids);

    <ID extends Serializable> void delete(Class<T> tClass, ID id);

    void update(Object t);

    <T> List<T> query(String jpaSql);

    <T> List<T> query(String jpaSql, List params);

    <T> List<T> query(String jpaSql, List params, int page, int pageSize);

}

