/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.TestWindow;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Doomed
 */
public class PackageList {
    
        public Collection<String> getPackages() {
        Set<String> packages = new HashSet<String>();
        for (Package aPackage : Package.getPackages()) {
            packages.add(aPackage.getName());
        }
        String classpath = System.getProperty("sun.boot.class.path");
            return null;

    }
    
}
