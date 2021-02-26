package com.managers;

import com.G;
import com.gui.Gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GuiManager implements Listeners.UserListener {

    private Gui gui;
    private final ArrayList<Listeners.GuiListener> mListeners = new ArrayList<>();

    public GuiManager() {
        gui = new Gui();
        setupKeyListener();
    }

    public void initGui() {
        EventQueue.invokeLater(() -> gui.setVisible(true));
    }

    private void setupKeyListener() {
        gui.getContentPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    notifyListeners(G.EVENT_USER_TOGGLE, System.currentTimeMillis());
                }
            }
        });
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
