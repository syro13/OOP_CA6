package com.dkit.oop.sd2.DAOs;

/** OOP Feb 2022
 *
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 * The Business Logic layer is only permitted to access the database by calling
 * methods provided in the Data Access Layer - i.e. by callimng the DAO methods.
 *
 */


import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Filters.SortByCapacity;
import com.dkit.oop.sd2.RocketObjects.IdCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;


public class MySqlRocketDao extends MySqlDao implements RocketDaoInterface
{

    @Override
    public List<Rockets> findAllRockets() throws DaoException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Rockets> rocketsList = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            connection = this.getConnection();

            String query = "SELECT * FROM ROCKETS";
            ps = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                int rocket_id = resultSet.getInt("ROCKET_ID");
                String rocket_name = resultSet.getString("ROCKET_NAME");
                String manufacturer = resultSet.getString("MANUFACTURER");
                String first_flight = resultSet.getString("FIRST_FLIGHT");
                int payload_capacity = resultSet.getInt("PAYLOAD_CAPACITY");
                Rockets r = new Rockets(rocket_id, rocket_name, manufacturer, first_flight, payload_capacity);
                rocketsList.add(r);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllRockets() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return rocketsList;     // may be empty
    }

    @Override
    public Rockets findRocketsByRocketID(int rocket_id) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Rockets rockets = null;
        List<Rockets> testList = null;
        IdCache cache = new IdCache();
        try
        {

            connection = this.getConnection();
            testList= findAllRockets();
            for (Rockets rocket: testList) {
                cache.add(rocket.getRocket_id());
            }
            if(cache.contains(rocket_id)){
                String query = "SELECT * FROM ROCKETS WHERE ROCKET_ID = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, rocket_id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next())
                {
                    int rocketID = resultSet.getInt("ROCKET_ID");
                    String rocket_name = resultSet.getString("ROCKET_NAME");
                    String manufacturer = resultSet.getString("MANUFACTURER");
                    String first_flight = resultSet.getString("FIRST_FLIGHT");
                    int payload_capacity = resultSet.getInt("PAYLOAD_CAPACITY");
                    rockets = new Rockets(rocketID, rocket_name, manufacturer, first_flight, payload_capacity);
                }
            }else{
                System.out.println("ID selected does not exist in database");
            }

        } catch (SQLException e)
        {
            throw new DaoException("findRocketsByRocketID() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return rockets;     // reference to User object, or null value
    }
    @Override
    public void deleteRocketByRocketID(int rocket_id) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            connection = this.getConnection();

            String query = "DELETE FROM ROCKETS WHERE ROCKET_ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rocket_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            throw new DaoException("deleteRocketByRocketID() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
    }

    @Override
    public void insertRocket(String rocket_name, String manufacturer, String date, int payload_capacity) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            connection = this.getConnection();

            String query = "INSERT INTO rockets (rocket_name, manufacturer, first_flight, payload_capacity)\n" +
                    "VALUES\n" +
                    "  (" +
                    "?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rocket_name);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, payload_capacity);

            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            throw new DaoException("deleteRocketByRocketID() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
    }
    @Override
    public List<Rockets> findRocketsUsingFilter(Comparator<Rockets> filter) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Rockets> list = new ArrayList<>();

        try
        {
            list = findAllRockets();
            list.sort(new SortByCapacity());
        } catch (SQLException e)
        {
            throw new DaoException("deleteRocketByRocketID() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return list;
    }

    public String findAllRocketsJson() throws DaoException {
        Gson gsonParser = new Gson();

        List<Rockets> rocketsList = findAllRockets();

        String gsonString = gsonParser.toJson(rocketsList);

        return gsonString;
    }
    public String findRocketsByIdJson(int id) throws DaoException{
        Gson gsonParser = new Gson();

        Rockets rocket = findRocketsByRocketID(id);

        String gsonString = gsonParser.toJson(rocket);

        return gsonString;
    }
}


