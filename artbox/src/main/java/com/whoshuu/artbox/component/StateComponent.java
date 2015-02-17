package com.whoshuu.artbox.component;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whoshuu.artbox.artemis.utils.JSONComponent;

public class StateComponent extends JSONComponent {

    public StateComponent() {
        states = new ArrayList<String>();
    }

    public String getState() {
        return current;
    }

    public void setState(String state) {
        if (states.contains(state)) {
            current = state;
        }
    }

    @Override
    public void fromJSON(JSONObject json, float x, float y, float angle) throws JSONException {
    	/* 
    	 * "type": "com.whoshuu.artbox.component.StateComponent",
         * "states": [
         *   "name_of_first_state",
         *   "name_of_second_state",
         *   ...
         *   "name_of_last_state"
         * ]
         *
         * The first state is assumed to be the first activated state.
         */
        JSONArray jsonStates = json.getJSONArray("states");
        for (int i = 0; i < jsonStates.length(); i++) {
            String state = jsonStates.getString(i);
            states.add(state);
            if (i == 0) {
                setState(state);
            }
        }
    }

    private ArrayList<String> states;
    private String current;
}
