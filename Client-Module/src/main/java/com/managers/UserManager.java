package com.managers;

import com.User;

import java.util.ArrayList;

import static com.G.*;

public class UserManager implements Listeners.NetworkingListener, Listeners.GuiListener {

    private final ArrayList<Listeners.UserListener> mListeners = new ArrayList<>();
    public static final ArrayList<User> USERS = new ArrayList<>();

    public UserManager() {

    }

    public void addUser(String id) {

    }

    public void startUser(String id, long t) {

    }

    public void stopUser(String id, long t) {

    }

    public void removeUser(String id) {

    }

    @Override
    public void onNetworkEvent(String event, Object... data) {
        if (event.equals(EVENT_USER_IN)) {
            addUser("");
        } else if (event.equals(EVENT_USER_START)) {
            startUser("", 0);
        } else if (event.equals(EVENT_USER_STOP)) {
            stopUser("", 0);
        } else if (event.equals(EVENT_USER_OUT)) {
            removeUser("");
        }
    }

    @Override
    public void onGuiEvent(String event, Object... data) {
        // TODO: switch all the desired events that comes from GUI
    }

    public void addListener(Listeners.UserListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeListener(Listeners.UserListener listener) {
        mListeners.remove(listener);
    }

    private void notifyListeners(String event, Object... data) {
        for (Listeners.UserListener l : mListeners) {
            l.onUserEvent(event, data);
        }
    }
}
