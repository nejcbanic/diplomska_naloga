/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nejcbanic.javaeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    public Map<String, ArrayList<JLabel>> retList(){
           // Location of file to read
        File file = new File(contentName+".txt");

        Map<String, ArrayList<JLabel>> dict = new HashMap<>();
       
        try {
 
            try (Scanner scanner = new Scanner(file).useDelimiter(";")) {
                while (scanner.hasNext()) {
                    String line = scanner.next().replace("\r\n", " ").replace("\n", " ");
                    String resWords = scanner.next().replace("\r\n", " ").replace("\n", " ");
                    String test[]= resWords.split(" ");
                    ArrayList<JLabel> test2 = new ArrayList<>();
                    for(String t:test){
                        if (!"".equals(t)){
                            test2.add(new JLabel(t));
                            
                        }
                    }
                    dict.put(line, test2);
                    
                }
            }
        } catch (FileNotFoundException e) {
        }
        return dict;       
    }   
}