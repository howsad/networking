package ru.sbt.networking;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

import static ru.sbt.networking.ServerSerialization.deserialize;
import static ru.sbt.networking.ServerSerialization.sendException;
import static ru.sbt.networking.ServerSerialization.serialize;

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
    public static void listen(int port, Object impl) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    try {
                        Object[] objects = deserialize(client);
                        Object[] arguments = Arrays.copyOfRange(objects, 1, objects.length);
                        Class<?>[] classes = Stream.of(arguments)
                                .map(Object::getClass)
                                .toArray(Class<?>[]::new);
                        Method m = impl.getClass().getMethod((String) objects[0], classes);
                        Object result = m.invoke(impl, arguments);
                        serialize(result, client);
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException
                            | SecurityException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e) {
                        sendException(e, client);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServerRegistrator.listen(5000, new CalculatorImpl());
    }
}