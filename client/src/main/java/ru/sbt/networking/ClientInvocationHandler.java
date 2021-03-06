package ru.sbt.networking;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

import static ru.sbt.networking.ClientSerialization.deserialize;
import static ru.sbt.networking.ClientSerialization.serialize;

public class ClientInvocationHandler implements InvocationHandler {
    private final String host;
    private final int port;

    public ClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        try (Socket socket = new Socket(host, port)) {
            serialize(method, args, socket);
            return deserialize(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
