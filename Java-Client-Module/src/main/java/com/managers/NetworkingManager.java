package com.managers;

import com.G;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.ArrayList;

import static com.G.*;

public class NetworkingManager implements Listeners.UserListener {

    protected Socket socket;
    private final ArrayList<Listeners.NetworkingListener> mListeners = new ArrayList<>();
    private static final String[] EVENTS = {
            EVENT_USER_IN,
            EVENT_USER_OUT,
            EVENT_USER_START,
            EVENT_USER_START,
            EVENT_USER_STOP
    };

    public NetworkingManager() {
        init();
    }

    private void init() {
        socket = IO.socket(URI.create(SERVER_URL));
        socket.on(Socket.EVENT_CONNECT, d -> System.out.println("This client is connected to the Node server."));
        for (String evt : EVENTS) socket.on(evt, data -> notifyListeners(evt, data));
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
        if (event.equals(EVENT_USER_TOGGLE)) {
            socket.emit(EVENT_USER_TOGGLE, data);
        }
    }
}
