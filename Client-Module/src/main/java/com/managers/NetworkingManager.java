package com.managers;

import com.G;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.ArrayList;

import static com.G.*;

public class NetworkingManager implements Listeners.UserListener {

    private final ArrayList<Listeners.NetworkingListener> mListeners = new ArrayList<>();

    public NetworkingManager() {
        init();
    }

    private void init() {
        Socket socket = IO.socket(URI.create(SERVER_URL));

        socket.on(Socket.EVENT_CONNECT, d -> System.out.println("This client is connected to the Node server."));

        socket.on(EVENT_USER_IN, data -> notifyListeners(EVENT_USER_IN, data[0]));

        socket.on(EVENT_USER_OUT, data -> notifyListeners(EVENT_USER_OUT, data[0]));

        socket.on(EVENT_USER_START, data -> notifyListeners(EVENT_USER_START, data[0]));

        socket.on(EVENT_USER_STOP, data -> notifyListeners(EVENT_USER_STOP, data[0]));

        socket.connect();
    }

    public void addListener(Listeners.NetworkingListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeListener(Listeners.NetworkingListener listener) {
        mListeners.remove(listener);
    }

    public void notifyListeners(String event, Object... data) {
        for (Listeners.NetworkingListener l : mListeners) {
            l.onNetworkEvent(event, data);
        }
    }

    @Override
    public void onUserEvent(String event, Object... data) {
        // TODO...
    }
}
