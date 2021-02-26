package com;

import com.managers.GuiManager;
import com.managers.NetworkingManager;
import com.managers.UserManager;

public class Main {

    public static void main(String[] args) {
        final NetworkingManager nm = new NetworkingManager();
        final UserManager um = new UserManager();
        final GuiManager gm = new GuiManager();

        nm.addListener(um);

        um.addListener(nm);
        um.addListener(gm);

        gm.addListener(um);

        gm.start();
    }
}
