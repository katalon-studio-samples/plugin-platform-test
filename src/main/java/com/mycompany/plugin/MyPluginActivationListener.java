package com.mycompany.plugin;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.controller.ExecutionController;
import com.katalon.platform.api.extension.PluginActivationListener;
import com.katalon.platform.api.service.ApplicationManager;

public class MyPluginActivationListener implements PluginActivationListener {
    @Override
    public void afterActivation(Plugin plugin) {
        System.out.println("Hello, my plugin is: " + plugin.getPluginId());

        ExecutionController ctl = ApplicationManager.getInstance()
                .getControllerManager()
                .getController(ExecutionController.class);

        System.out.println("Running mode: " + ctl.getRunningMode());

        System.out.println("JRE location: " + ctl.getJreLocation());
    }
}
