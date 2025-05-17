package com.window.ui;

import java.awt.event.ActionEvent;

@FunctionalInterface
public interface SaveAction {
    boolean execute(ActionEvent e);
}
