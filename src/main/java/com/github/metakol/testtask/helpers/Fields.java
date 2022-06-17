package com.github.metakol.testtask.helpers;

import javafx.scene.control.TextInputControl;

public class Fields {
    public static boolean fieldsAreNotEmpty(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            if (field.getText().trim().equals("")) {
                return false;
            }
        }
        return true;
    }
}
