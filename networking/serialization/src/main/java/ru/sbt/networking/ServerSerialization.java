package ru.sbt.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by user16 on 07.09.2016.
 */
public class ServerSerialization {
    public static void serialize(Object result, Socket socket) {
        try (ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream())) {
            stream.writeObject(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object deserialize(Socket socket) {
        try (ObjectInputStream stream = new ObjectInputStream(socket.getInputStream())) {
            try {
                return stream.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
