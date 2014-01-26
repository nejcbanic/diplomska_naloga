/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.TestWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JLabel;

/**
 *
 * @author Doomed
 */
class ReservedWords{
    
    private final String contentName;
    public ReservedWords(String contentName){
        this.contentName = contentName;
    }
    public List<JLabel> retList(){
           // Location of file to read
        File file = new File(contentName+".txt");
        List<JLabel> myList = new ArrayList<JLabel>();
       
        try {
 
            Scanner scanner = new Scanner(file);
 
            while (scanner.hasNext()) {
                String line = scanner.next();
                myList.add(new JLabel(line));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
        return myList;
        
    }
    
}
