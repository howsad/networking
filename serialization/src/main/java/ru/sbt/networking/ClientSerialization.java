package ru.sbt.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by user16 on 07.09.2016.
 */
public class ClientSerialization {
    public static void serialize(Method m, Object[] objects, Socket socket) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
        stream.writeObject(m.getParameterCount());
        stream.writeObject(m.getName());
        if (objects != null) {
            for (Object o : objects) {
                stream.writeObject(o);
            }
        }
    }

    public static Object deserialize(Socket socket) throws Exception {
        ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
        try {
            Object o = stream.readObject();
            if (o instanceof Exception) {
                throw (Exception) o;
            }
            return o;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
