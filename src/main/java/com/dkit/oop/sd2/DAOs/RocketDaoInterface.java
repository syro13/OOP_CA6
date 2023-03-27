package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public interface RocketDaoInterface {
    public List<Rockets> findAllRockets() throws DaoException;

    public Rockets findRocketsByRocketID(int rocket_id) throws DaoException;
    public void deleteRocketByRocketID(int rocket_id) throws DaoException;
    public void insertRocket(String rocket_name, String manufacturer, String date, int payload_capacity) throws DaoException;
}
