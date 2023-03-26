package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlRocketDao;
import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RocketDaoInterface IRocketDao = new MySqlRocketDao();
        try{
            System.out.println("\nCall findAllRockets()");
            List<Rockets> rockets = IRocketDao.findAllRockets();

            if( rockets.isEmpty() ){
                System.out.println("There are no Rockets");
            }else{
                for(Rockets rocket : rockets){
                    System.out.println("Rockets" + rocket.toString());
                }
            }

            System.out.println("\nCall: findRocketsByID()");
            int rocket_id = 1;
            Rockets rockets1 = IRocketDao.findRocketsByRocketID(rocket_id);
            if(rockets1 != null){
                System.out.println("Rocket with id: " + rocket_id + " was found: " + rockets1);
            }else{
                System.out.println("Rocket_ID" + rocket_id + "is not valid");
            }
        }catch ( DaoException e ){
            e.printStackTrace();
        }
    }
}
