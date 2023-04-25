package com.dkit.oop.sd2.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

import com.dkit.oop.sd2.DAOs.MySqlRocketDao;
import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Filters.SortByCapacity;
public class Server {
    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    public void start()
    {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("Server Message: Server started. Listening for connections...");

            while(true)
            {
                Socket socket = ss.accept();  // wait for client to connect, and open a socket with the client

                System.out.println("Server Message: A Client has connected.");

                Scanner in = new Scanner(socket.getInputStream());
                String command = in.nextLine();

                System.out.println("Server message: Received from client : \"" + command + "\"");

                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os, true);
                if(command.startsWith("Time"))
                {
                    LocalTime time =  LocalTime.now();
                    out.print(time);
                }
                else if(command.startsWith("Echo"))
                {
                    command = command.substring(5); // strip off the 'Echo ' part
                    out.print( command+"\n");
                }
                else
                {
                    out.print("I'm sorry i don't understand :(\n");
                }
                out.flush();  // force the response to be sent
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Server Message: IOException: " + e);
        }
    }
}
