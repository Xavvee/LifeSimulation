package agh.ics.oop.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class StartConfigurations extends HashMap<String, String> {
    public int getNumber(Object key) {
        return Integer.parseInt(super.get(key));
    }
}
