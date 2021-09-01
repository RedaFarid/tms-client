package soulintec.com.tmsclient.Graphics.Controls;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Utilities {

//    public static  String iP = "http://192.168.0.7:8086";
      public static  String iP = "http://localhost:8086";

    public static Double round(int scale, Double number) {
        Double scaling = 1.0;
        for (int x = 0; x < scale; x++) {
            scaling *=10.0;
        }
        return (double)Math.round(number * scaling) / scaling;
    }

    public static void upperCase(TextField field) {
        field.setTextFormatter(new TextFormatter<>((change)->{
            change.setText(change.getText().toUpperCase());
            return change;
        }
        ));
    }
}
