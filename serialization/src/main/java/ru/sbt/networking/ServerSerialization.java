package ru.sbt.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by user16 on 07.09.2016.
 */
public class ServerSerialization {
    public static void serialize(Object result, Socket socket) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
        stream.writeObject(result);
    }

    public static Object[] deserialize(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
        try {
            int nObjects = (int) stream.readObject() + 1;
            Object[] objects = new Object[nObjects];
            for (int i = 0; i < nObjects; i++) {
                objects[i] = stream.readObject();
            }
            return objects;
        } catch (ClassNotFoundException e) {
            sendException(e, socket);
            throw e;
        }
    }

    public static void sendException(Exception e, Socket socket) throws IOException {
        serialize(e, socket);
    }
}
