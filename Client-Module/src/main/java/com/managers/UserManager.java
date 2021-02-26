package com.managers;

import com.G;
import com.Solve;
import com.User;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.G.*;

public class UserManager implements Listeners.NetworkingListener, Listeners.GuiListener {

    private final ArrayList<Listeners.UserListener> mListeners = new ArrayList<>();
    public static final ArrayList<User> USERS = new ArrayList<>();

    public UserManager() {
    }

    public void addUser(String id) {
        if (!id.isEmpty()) {
            for (User u : USERS) {
                if (u.id.equals(id)) {
                    return;
                }
            }
            USERS.add(new User(id));
            notifyListeners(EVENT_USER_ADDED, "pass");
        }
    }

    public void startUser(Object... data) {
        try {
            JSONObject object = new JSONObject(data[0]);
            if (object.has("id")) {
                final var id = object.getString("id");
                for (User user : USERS) {
                    if (user.id.equals(id)) {
                        user.started = object.getBoolean("started");
                        user.finished = object.getBoolean("finished");
                        notifyListeners(EVENT_USER_START, "pass");
                        break;
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    public void stopUser(Object... data) {
        try {
            JSONObject object = new JSONObject(data[0]);
            if (object.has("id")) {
                final var id = object.getString("id");
                for (User user : USERS) {
                    if (user.id.equals(id)) {
                        user.started = object.getBoolean("started");
                        user.finished = object.getBoolean("finished");
                        final Solve solve = new Solve(
                                object.getString("scramble"),
                                object.getLong("lastTime"));
                        user.lastTime = solve.time;
                        user.solves.add(solve);
                        notifyListeners(EVENT_USER_STOP, "pass");
                        break;
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    public void removeUser(Object... data) {
        final var targetId = (String) data[0];
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).id.equals(targetId)) {
                USERS.remove(i);
                notifyListeners(EVENT_USER_OUT);
                break;
            }
        }
    }

    @Override
    public void onNetworkEvent(String event, Object... data) {
        // data: array of IDs
        // data: id, started, finished
        // data: id, started, finished, lastTime, scramble
        // data: id
        switch (event) {
            case EVENT_USER_IN -> {
                final var parse = G.jsonArrayToList(data);
                if (parse != null && !parse.isEmpty()) {
                    final var check = USERS.stream().anyMatch(parse::contains);
                    if (!check) {
                        parse.forEach(this::addUser);
                    }
                }
            }
            case EVENT_USER_START -> startUser(data);
            case EVENT_USER_STOP -> stopUser(data);
            case EVENT_USER_OUT -> removeUser(data);
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
