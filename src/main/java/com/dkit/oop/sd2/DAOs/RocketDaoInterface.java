package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.Comparator;
import java.util.List;

public interface RocketDaoInterface {
    List<Rockets> findAllRockets() throws DaoException;

    Rockets findRocketsByRocketID(int rocket_id) throws DaoException;
    void deleteRocketByRocketID(int rocket_id) throws DaoException;
    void insertRocket(String rocket_name, String manufacturer, String date, int payload_capacity) throws DaoException;
    List<Rockets> findPlayersUsingFilter(Comparator<Rockets> filter) throws DaoException;

    void findAllPlayerJson() throws DaoException;
    void findRocketsByIdJson(int id) throws DaoException;
}
