package com.dkit.oop.sd2.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

import com.dkit.oop.sd2.DAOs.MySqlRocketDao;
import com.dkit.oop.sd2.DAOs.RocketDaoInterface;
import com.dkit.oop.sd2.DTOs.Rockets;
import com.dkit.oop.sd2.Exceptions.DaoException;
import com.dkit.oop.sd2.Filters.SortByCapacity;
import com.google.gson.JsonObject;

public class Server {
    public static RocketDaoInterface IRocketDao = new MySqlRocketDao();

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
    {
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = socketReader.readLine()) != null) {
                    if (message.startsWith("1")) {
                        String gsonString = IRocketDao.findRocketsByIdJson(Integer.parseInt(message.substring(2)));
                        if (gsonString == null) {
                            System.out.println("Error");
                        } else {
                            System.out.println("No Error finding the rocket");
                            socketWriter.println(gsonString);
                        }
                    } else if (message.startsWith("2")) {
                        String gsonString = IRocketDao.findAllRocketsJson();
                        socketWriter.println(gsonString);
                    } else if (message.startsWith("3")) {
                        String[] details = message.substring(1).split(",");
                        String name = details[0].substring(1);
                        String manufacturer = details[1].substring(1);
                        String date = details[2].substring(1);
                        int payloadCapacity = Integer.parseInt(details[3].substring(1));
                        IRocketDao.insertRocket(name, manufacturer, date, payloadCapacity);
                    } else if (message.startsWith("4")) {
                        IRocketDao.deleteRocketByRocketID(Integer.parseInt(message.substring(2)));
                        String gsonString = IRocketDao.findRocketsByIdJson(Integer.parseInt(message.substring(2)));
                        socketWriter.println(gsonString);
                    } else {
                        socketWriter.print("I'm sorry i don't understand :(\n");
                    }
                }

                socket.close();

            } catch (IOException | DaoException ex) {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

}
