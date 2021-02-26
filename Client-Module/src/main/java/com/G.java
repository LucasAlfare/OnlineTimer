package com;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class G {

    public static final String SERVER_URL = "http://localhost:3000";

    public static final String EVENT_USER_IN = "EVENT_USER_IN";
    public static final String EVENT_USER_OUT = "EVENT_USER_OUT";
    public static final String EVENT_USER_START = "EVENT_USER_START";
    public static final String EVENT_USER_STOP = "EVENT_USER_STOP";

    public static final String EVENT_USER_ADDED = "EVENT_USER_ADDED";
    public static final String EVENT_USER_TOGGLE = "EVENT_USER_TOGGLE";

    public static ArrayList<String> jsonArrayToList(Object... data) {
        String raw = (String) data[0];
        if (!raw.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(raw);
                ArrayList<String> ids = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ids.add((String) jsonArray.get(i));
                }
                System.out.println("[G] IDs from server: " + ids);
                return ids;
            } catch (Exception ignored) {
                String err =
                                """
                                Was not possible parse the array from of the server response. 
                                The data received was: 
                                """.concat(Arrays.toString(data));
                System.err.println(err);
            }
        }
        return null;
    }
}
