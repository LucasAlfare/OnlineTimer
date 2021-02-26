package com.managers;

import com.G;
import com.User;
import com.gui.Gui;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static com.G.*;
import static com.managers.UserManager.USERS;

public class GuiManager implements Listeners.UserListener {

    private Gui gui;
    private final ArrayList<Listeners.GuiListener> mListeners = new ArrayList<>();

    public GuiManager() {
        gui = new Gui();
        setupKeyListener();
    }

    public void start() {
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

    @Override
    public void onUserEvent(String event, Object... data) {
        if (event.equals(EVENT_USER_ADDED) ||
                event.equals(EVENT_USER_STOP) ||
                event.equals(EVENT_USER_OUT)) {
            updateTableModel();
        }
    }

    private void updateTableModel() {
        //gui.model.getDataVector().clear();
        gui.model = new DefaultTableModel();
        for (User user : USERS) {
            final var times = new Object[user.solves.size()];
            for (int i = 0; i < times.length; i++) {
                times[i] = user.solves.get(i).time;
            }
            gui.model.addColumn(user.id, times);
        }
        gui.timesView.setModel(gui.model);
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
}
