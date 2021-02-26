package com.managers;

public class Listeners {

    interface NetworkingListener {
        void onNetworkEvent(String event, Object... data);
    }

    interface UserListener {
        void onUserEvent(String event, Object... data);
    }

    interface GuiListener {
        void onGuiEvent(String event, Object... data);
    }
}
