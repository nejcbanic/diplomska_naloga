/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.TestWindow;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.DirectAndRawInputEnvironmentPlugin;

/**
 *
 * @author Doomed
 */

public class ControllerJoystick {

    public ArrayList<Controller> contList;
    public static Component[] joyComp;

 
    public ArrayList<Controller> getControllers(){
        
        Controller[] rawsCon;
        contList = new ArrayList<Controller>();
        DirectAndRawInputEnvironmentPlugin directEnv = new DirectAndRawInputEnvironmentPlugin();
        if (directEnv.isSupported()) {
                rawsCon = directEnv.getControllers();
        } else {
                rawsCon = ControllerEnvironment.getDefaultEnvironment().getControllers();
        }
        
         for (Controller rawsCon1 : rawsCon) {
          if (
                    rawsCon1.getType() == Controller.Type.STICK ||
                    rawsCon1.getType() == Controller.Type.GAMEPAD
                    )
            {
                // Add new controller to the list of all controllers.
                contList.add(rawsCon1);          
            }
     } 
        
       return contList;
      }   
}
