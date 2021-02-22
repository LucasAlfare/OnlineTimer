package com.managers;

import java.util.ArrayList;

public class GuiManager implements Listeners.UserListener {

    private final ArrayList<Listeners.GuiListener> mListeners = new ArrayList<>();

    public GuiManager() {

    }

    public void addListener(Listeners.GuiListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeListener(Listeners.GuiListener listener) {
        mListeners.remove(listener);
    }

    public void notifyListeners(String event, Object... data) {
        for (Listeners.GuiListener l : mListeners) {
            l.onGuiEvent(event, data);
        }
    }

    @Override
    public void onUserEvent(String event, Object... data) {

    }
}
