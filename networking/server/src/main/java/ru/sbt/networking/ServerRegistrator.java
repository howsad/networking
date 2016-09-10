package ru.sbt.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/*
* Client:
* createProxy
*     send method name + args to server
 *    receive return value from server and return it
* */

/*
* server:
* listen host + port
* read methodName + args
* invoke method via reflection
* send return value to client
*
* */

public class ServerRegistrator {
    public static void listen(String host, int port, Object impl) {
        try (Socket socket = new Socket(host, port)) {
            try (ObjectInputStream stream = new ObjectInputStream(socket.getInputStream())) {
                try {
                    String methodName = (String) stream.readObject();
                    Method m = impl.getClass().getDeclaredMethod(methodName);
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        ServerRegistrator.listen("localhost", 5000, new CalculatorImpl());
    }
}