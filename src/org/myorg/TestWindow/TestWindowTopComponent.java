/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.TestWindow;
 
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import net.java.games.input.*;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.Utilities;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.UndoRedo;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */


@ConvertAsProperties(
        dtd = "-//org.myorg.TestWindow//TestWindow//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "TestWindowTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.myorg.TestWindow.TestWindowTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TestWindowAction",
        preferredID = "TestWindowTopComponent")
@Messages({
    "CTL_TestWindowAction=TestWindow",
    "CTL_TestWindowTopComponent=TestWindow Window",
    "HINT_TestWindowTopComponent=This is a TestWindow window"
})

public final class TestWindowTopComponent extends TopComponent {

    private static  MouseListener mouseListener1;
    private static  MouseListener mouseListener2;
    private static  MouseListener mouseListenerMethods;
    private static  MouseListener mouseListenerRW;
    private JEditorPane jEditorPane2 = null;
    private final ReservedWords resWords;
    private final  Map<String, ArrayList<JLabel>>  f;
    private ArrayList<Controller> newsticks;
    private final   Set<File> content  = new HashSet<>();
    private final   Map<String, ArrayList<String>> classFinal = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final  Map<String, ArrayList<String>> packageFinal = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final  String[][] favoritesAndVariables = {{"java.lang.String","java.lang.System","java.lang.Integer"
                                               ,"java.util.ArrayList","java.util.HashMap","java.lang.Object"
                                               ,"java.lang.Math","java.lang.Class","java.util.Date","java.util.Iterator"},
                                            {"i","j","k","l","m","n","o","p"}};

    private static final Map<String, ArrayList<String>> classFavAndVar = new HashMap<>();
    private Component[] components;
    private boolean period = false;
    private final String firstRow[] = {"1","2","3","4","5","6","7","8","9","0","-","+","Backspace"};//BackSpace
    private final String secondRow[] = {"Q","W","E","R","T","Y","U","I","O","P","[","]","\\"};
    private final String thirdRow[] = {"A","S","D","F","G","H","J","K","L",":",";","Enter"};
    private final String fourthRow[] = {"Shift","Z","X","C","V","B","N","M",",",".","?","(", ")"};
    private final String fifthRow[]={"Space" ,"<" ,">" };
    private JTextField searchField;
    private boolean upperCase = true;
    private JPanel scrollS;
    private JPopupMenu popupPackage;
    private boolean leftButton = false;
    private JTextPane outputWindow;
    private Point mousePos;
    private int currX;
    private int currY;
    private int newX;
    private int newY;
    private Robot r;
    private boolean codeComplete = false;
    private Component Xaxis;
    private Component Yaxis;
    private JButton run;
    private Map <String, Component> buttons;
    private volatile boolean isRunningThread = true;
    
    public TestWindowTopComponent() throws IOException, BadLocationException{
            resWords = new ReservedWords("test");
            f = resWords.retList();
            
            String loc = System.getProperty("sun.boot.class.path");

            classFavAndVar.put("Favorites", new ArrayList<>(Arrays.asList(favoritesAndVariables[0])));
            classFavAndVar.put("Variables", new ArrayList<>(Arrays.asList(favoritesAndVariables[1])));
            for (String path:loc.split(";")){
                 File test = new File(path);
                 if (test.isDirectory() && test.exists())
                     recursiveSearch(path);
                 else
                     content.add(test);         
            }
            for (File file: content) {
              if (file.getPath().endsWith(".jar") && file.exists()){
                  getJarContent(file.getPath());
              }
            }

           
             initComponents();
             initEditor();
             initMyComp();
    }

    public final void initEditor(){
        
        outputWindow = new JTextPane();            
        jEditorPane2 = new JEditorPane();
        run = new JButton("Run");
        run.setBackground(Color.green);

        jEditorPane2.setContentType("text/x-java");
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/x-java");
     
        jEditorPane2.setEditorKit(kit);
        jEditorPane2.getDocument().putProperty("mimeType", "text/x-java");
       
        BaseDocument doc = Utilities.getDocument(jEditorPane2); 
  
        if (doc instanceof NbDocument.CustomEditor) { 
            NbDocument.CustomEditor ce = (NbDocument.CustomEditor) doc;
       
            editorWindow.add(ce.createEditor(jEditorPane2), BorderLayout.CENTER);
          
        } else {
            editorWindow.add(new JScrollPane(jEditorPane2), BorderLayout.CENTER);
        }

        toolbarEditorPane.add(Utilities.getEditorUI(jEditorPane2).getToolBarComponent(), BorderLayout.CENTER);
        toolbarEditorPane.add(run);
        editorWindow.add(new JScrollPane(outputWindow), BorderLayout.CENTER);
        jEditorPane2.setText("public class Test{\n\tpublic static int spr1;\n\tprivate int spr2;\n\tpublic static void main(String[]args){\n\t\tSystem.out.println(\"Hello world\");\n\t}\n}");
    }
    
    private  void addToComboBox(String msg, JComboBox comboBox){
        comboBox.addItem(msg);
    }
    
    private JPanel returnExceptions(){
        
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,3));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Exceptions");
       form.setBorder(title);
       ArrayList<String> exceptionList = packageFinal.get("java/lang");
       exceptionList.addAll(packageFinal.get("java/io"));
       Collections.sort(exceptionList);
       for (String exp: exceptionList){
            if (exp.contains("Exception") || exp.contains("Error") ){
                JLabel methodLabel = new JLabel(exp);
                methodLabel.setHorizontalAlignment(SwingConstants.LEFT);
                methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
                methodLabel.setForeground(Color.blue); 
                methodLabel.addMouseListener(mouseListenerRW);
                form.add(methodLabel);
            }
       }
       return form;
    }
    
    private JPanel returnPanelVariables (){
        
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,4));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Variable name suggestions");
       form.setBorder(title); 
       
       if (classFavAndVar.isEmpty())
           return null;
       for (String classF: classFavAndVar.get("Variables")){
            JLabel methodLabel = new JLabel(classF);
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue);
            form.add(methodLabel);
            methodLabel.addMouseListener(mouseListenerRW);
       }
       return form;
    }
    private JPanel returnPanelFavorites (){
        
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,2));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Favorites");
       form.setBorder(title); 
       
       if (classFavAndVar.isEmpty())
           return null;
       for (String classF: classFavAndVar.get("Favorites")){
            JLabel methodLabel = new JLabel(classF);
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue);
            form.add(methodLabel);
            methodLabel.addMouseListener(mouseListener2);
       }
       return form;
    }
  
    private JPanel returnPanelFields(Class newClass){
                            
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,5));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Fields");
       form.setBorder(title); 
       Field[] fields = newClass.getFields();
     
       for (Field field:fields)
       {
            JLabel methodLabel = new JLabel(field.getName());
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue); 
            methodLabel.addMouseListener(mouseListener1);
            form.add(methodLabel);
       }
        return form;       
        
    }
    
    private JPanel returnPanelMethods(Class newClass){

       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,4));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Methods");
       form.setBorder(title); 
       Method[] methods = newClass.getDeclaredMethods();       
       for (Method method:methods)
       {
        if(Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers()) ){
            Class parameters[] = method.getParameterTypes();

            String fullName = "<html><b>"+"Method name: "+method.getName()+ "</b><br>";
            if (parameters.length != 0){
                fullName += "Parameters:<ul>";
                for (Class parameter: parameters)
                        fullName +="<li>"+parameter.getSimpleName()+"</li>";
                fullName += "</ul></html>";

            }else{
                fullName+="No parameters! </html>";
            }
            JLabel methodLabel = new JLabel(method.getName());
            
            methodLabel.setToolTipText(fullName);
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue); 
            methodLabel.addMouseListener(mouseListenerMethods);
            form.add(methodLabel);
     
        }
       }
        return form;
    }
    
    private void reservedWords(){

        for (String key : f.keySet()){
            ArrayList<JLabel> value = f.get(key);
            JPanel form = new JPanel();
            form.setLayout(new GridLayout(0,5));
            TitledBorder title;
            title = BorderFactory.createTitledBorder(key);
            form.setBorder(title);
            for (JLabel test: value) {
                test.setHorizontalAlignment(SwingConstants.CENTER);
                test.setFont(new Font("Monospaced", Font.PLAIN, 13));
                test.setForeground(Color.blue);
                test.addMouseListener(mouseListenerRW);
                form.add(test);
            }
            rwPane.add(form);
        }
    }
    private int compileCode() throws IOException{
        OutputStream out = new OutputStream() {
            @Override
            public void write( int b) throws IOException {
                if (!codeComplete)
                    updateTextPane(String.valueOf((char) b));
            }

            private void updateTextPane( String string) {
                SwingUtilities.invokeLater(() -> {
                    Document doc = outputWindow.getDocument();
                    try {
                        doc.insertString(doc.getLength(), string, null);
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    outputWindow.setCaretPosition(doc.getLength() - 1);
                });
            }
        };

        PrintWriter output = null;
        try {
            output = new PrintWriter(jEditorPane2.getText().split("class")[1].split("[^a-zA-Z0-9']+")[1]+".java");
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        String codeCompleteText = jEditorPane2.getText();
        if(codeComplete){
 
            codeCompleteText = codeCompleteText.substring(0,jEditorPane2.getCaretPosition()-1)+codeCompleteText.substring(jEditorPane2.getCaretPosition());
            System.out.println(codeCompleteText);
        }
        output.println(codeCompleteText);
        output.close();
        String fileToCompile =jEditorPane2.getText().split("class")[1].split("[^a-zA-Z0-9']+")[1]+".java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileRes = compiler.run(null, null, out, fileToCompile);
        out.close();
        return compileRes;
    }
    private void runCode() throws IOException{

        StyledDocument doc = outputWindow.getStyledDocument();
        if(compileCode()== 0){
            try {
                Process p = Runtime.getRuntime().exec("java "+jEditorPane2.getText().split("class")[1].split("[^a-zA-Z0-9']+")[1]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    try {
                        doc.insertString(doc.getLength(), line+"\n",null );
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            } catch (IOException ex) {                  
            }
        }else{
            try {
                doc.insertString(doc.getLength(), "Compilation was not successful\n",null );

            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    public void refreshComboBoxes(){
        reservedWordsCB.removeAllItems();
        reservedWordsCB.repaint();
        runCodeCB.removeAllItems();
        runCodeCB.repaint();
        leftClickCB.removeAllItems();
        leftClickCB.repaint();
        searchTabCB.removeAllItems();
        searchTabCB.repaint();
        favoritesTabCB.removeAllItems();
        favoritesTabCB.repaint();
        classCB.removeAllItems();
        classCB.repaint();
    }
    public void initMyComp()  {
        run.addActionListener((ActionEvent e) -> {
            try {
                runCode();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    mouseListenerMethods = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){ 
            JLabel jc = (JLabel)e.getSource();
            int caretPos = jEditorPane2.getCaretPosition();      
            try {
                          
                String fin = jc.getText()+" ("; 
                    if (jc.getToolTipText().contains("Parameters:")){
                        List<String> par= Arrays.asList( jc.getToolTipText().replaceAll("^.*?<li>", "").split("</li>.*?(<li>|$)"));
                        if (!par.isEmpty()){
                            for (String par_f:par)
                                fin+=" ,";
                        }
                        fin = fin.substring(0, fin.lastIndexOf(",") - 1)+" )";
                    }else
                       fin+=")" ;
                jEditorPane2.getDocument().insertString(caretPos, fin, null);

            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;
                test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        @Override
        public void mouseExited(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;

                test.setBorder(null);
            }
        }
        
    };
    mouseListenerRW = new MouseAdapter(){
        
         @Override
        public void mouseClicked(MouseEvent e){
            JLabel jc = (JLabel)e.getSource();
            int caretPos = jEditorPane2.getCaretPosition();
             try {
                if ("main".equals(jc.getText())){
                   jEditorPane2.getDocument().insertString(caretPos, "public static void main (String[]args){\n}", null);
               }else if("try".equals(jc.getText())){
                   jEditorPane2.getDocument().insertString(caretPos, "try{\n\n}catch( ){}", null);
                   jTabbedPane1.setSelectedIndex(3);
               }else
                   jEditorPane2.getDocument().insertString(caretPos, jc.getText()+" ", null);
             } catch (BadLocationException ex) {
                 Exceptions.printStackTrace(ex);
             }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;
                test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        @Override
        public void mouseExited(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;

                test.setBorder(null);
            }
        }  
    };
    mouseListener1 = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel jc = (JLabel)e.getSource();
                 int caretPos = jEditorPane2.getCaretPosition();   
                   
                 try {

                    if (jc.getText().contains("\\.")){
                        jEditorPane2.getDocument().insertString(caretPos, jc.getText().split("\\.")[jc.getText().split("\\.").length-1], null);
                    }else{
                        jEditorPane2.getDocument().insertString(caretPos, jc.getText(), null);
                    }    
                     
                 } catch(BadLocationException ex) {
                 }                
            }else if(source instanceof JButton){
                 JButton jc = (JButton)e.getSource();

                 int caretPos = searchField.getCaretPosition();
                 try {
                     if ("Backspace".equals(jc.getText())){                          
                            searchField.getDocument().remove(caretPos-1,1);
                     }else if ("Shift".equals(jc.getText())){
                         upperCase = !upperCase;
                     } else if ("<".equals(jc.getText())){                            
                            searchField.setCaretPosition(caretPos-1);
                     }else if (">".equals(jc.getText())){                            
                            searchField.setCaretPosition(caretPos+1);
                     }else if ("Space".equals(jc.getText())){
                         searchField.getDocument().insertString(caretPos, " ", null);
                     }else{
                         if(upperCase )
                            searchField.getDocument().insertString(caretPos, jc.getText(), null);
                         else
                            searchField.getDocument().insertString(caretPos, jc.getText().toLowerCase(), null);
                     }
                 } catch(BadLocationException | IllegalArgumentException ex) {                         
                 }      
            }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;
                test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        @Override
        public void mouseExited(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;

                test.setBorder(null);
            }
        }  
    }; 

    jEditorPane2.addKeyListener(new KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
        }          
        @Override
        public void keyReleased(KeyEvent e) {
        }
        @Override
        public void keyTyped(KeyEvent e) {                
            period = e.getKeyChar() == '.';
            if(period && popupPackage!=null){
//                    popupPackage.show(jEditorPane2, jEditorPane2.getX(), jEditorPane2.getY());
            }
        }
    });

    jEditorPane2.getDocument().addDocumentListener(new DocumentListener(){

        @Override
        public void changedUpdate(DocumentEvent arg0){}
        @Override
        public void insertUpdate(DocumentEvent arg0){
            try {
                update(arg0);
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            } 
        }

        @SuppressWarnings("null")
        public void update(DocumentEvent e) throws BadLocationException, Exception{

            Document doc = e.getDocument();                     
            String output  = doc.getText(0, jEditorPane2.getCaretPosition());           

            if (period){
                codeComplete = false;                    
                output = output.substring(output.lastIndexOf("\n")+1);
                output = output.substring(output.lastIndexOf(" ")+1);
                output = output.trim();
                Class newClass;
                String test = null;
                ArrayList<String> temp = null;

                if (!(output.charAt(0) == '.')){
                    System.out.println(output);
                    test = output.split("\\.")[0];
                    temp  = classFinal.get(test);
                }
                if (temp!= null){
                    try{  
//                            popupPackage = null;
//                            if(temp.size()>1){
//                                Rectangle rectangle = jEditorPane2.modelToView( jEditorPane2.getCaretPosition());
//                               
//                                popupPackage = new JPopupMenu();
//                                for (String popupItem: temp){
//                                    JMenuItem menuItem = new JMenuItem(popupItem);
//                                    popupPackage.add(menuItem);
//                                }
//                              
//                                 popupPackage.show(jEditorPane2, rectangle.x, rectangle.y + rectangle.height);
//                            }else{
                            newClass = Class.forName(temp.get(0));
                            if (output.split("\\.").length > 1){
                                newClass = Class.forName(newClass.getField(output.split("\\.")[output.split("\\.").length -1]).getType().getCanonicalName());
                            }

//                            }

                        classResultsPanel.removeAll();
                        classResultsPanel.repaint();
                        if (newClass.getFields().length !=0){
                            classResultsPanel.add(returnPanelFields(newClass));
                            jTabbedPane1.setSelectedIndex(4);
                        }
                        if (newClass.getDeclaredMethods().length !=0){
                            classResultsPanel.add(returnPanelMethods(newClass));
                            jTabbedPane1.setSelectedIndex(4);
                        }
                    }catch( ArrayIndexOutOfBoundsException | NullPointerException | ClassNotFoundException | NoSuchFieldException exp){

                    }
                    codeComplete = false;
                }else if(output.equals(".")){
                    
                   
                  codeComplete = true;   
                   if(compileCode()== 0){ 
                        File root = new File(".");
                        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
                        classResultsPanel.removeAll();
                        classResultsPanel.repaint();
                        String className = jEditorPane2.getText().split("class")[1].split("[^a-zA-Z0-9']+")[1];
                        if (Class.forName(className, true, classLoader).getFields().length !=0){
                            classResultsPanel.add(returnPanelFields(Class.forName(className, true, classLoader)));
                            jTabbedPane1.setSelectedIndex(4);
                        }
                        if (Class.forName(className, true, classLoader).getDeclaredMethods().length !=0){
                            classResultsPanel.add(returnPanelMethods(Class.forName(className, true, classLoader)));
                            jTabbedPane1.setSelectedIndex(4);
                        }
                   }else{
                           classResultsPanel.removeAll();
                           classResultsPanel.repaint();
                           classResultsPanel.add(new JLabel ("Compilation was not successful!"));
                           jTabbedPane1.setSelectedIndex(4);

                   }
                   codeComplete = false;
                }
              period = false;
            }
        }

            @Override
            public void removeUpdate(DocumentEvent de) {

            }
    });
    toggleJoystick.addItemListener((ItemEvent ev) -> {
        if(ev.getStateChange()==ItemEvent.SELECTED){
            toggleJoystick.setText("Stop");
            toggleJoystick.setForeground(Color.red);
            isRunningThread = true;
           
        } else if(ev.getStateChange()==ItemEvent.DESELECTED){
            toggleJoystick.setText("Start");
            toggleJoystick.setForeground(Color.blue);
            isRunningThread = false;
        }
    });
          
    controllerList.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String item = (String)e.getItem();
                
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                   ArrayList <Component> buttons= new ArrayList<>();
                   refreshComboBoxes();
                   for (Controller selectedCont: newsticks){
                        if(selectedCont.getName().equals(item)){

                            try{
                                components = selectedCont.getComponents();
                            }
                            catch(NullPointerException ex){
                                System.exit(1);
                            }
                            for (Component component : components) {
                                if(component.getName().contains("Button")){
                                    buttons.add(component);
                                }                       
                            }           
                        }
                    }
                    for (Component buttonC:buttons){
                        String buttonName = buttonC.getName();
                        addToComboBox(buttonName, leftClickCB);
                        addToComboBox(buttonName, runCodeCB);
                        addToComboBox(buttonName, reservedWordsCB);
                        addToComboBox(buttonName, searchTabCB);
                        addToComboBox(buttonName, favoritesTabCB);
                        addToComboBox(buttonName, classCB);
                    } 
                }
                else
                {
                 
                }  
            }
        });
    JPanel pfinal = new JPanel(new GridLayout(6, 0));
    pfinal.setBorder(BorderFactory.createTitledBorder("Search results"));

    JPanel p = new JPanel(new GridLayout(1, 2));
    p.add(new JLabel("Results:"));
    searchField = new JTextField();
    searchField.setFont(new Font("Monospaced", Font.PLAIN, 13));

    p.add(searchField);
    pfinal.add(p);
    p = new JPanel(new GridLayout(1, firstRow.length));
    for(int i = 0; i < firstRow.length; ++i) 
    {
        JButton b = new JButton(firstRow[i]);
        b.addMouseListener(mouseListener1);
        p.add(b);
    }
    pfinal.add(p);

    p = new JPanel(new GridLayout(1, secondRow.length));
    for(int i = 0; i < secondRow.length; ++i) 
    {
        JButton b = new JButton(secondRow[i]);
        b.addMouseListener(mouseListener1);
        p.add(b);
    }
    pfinal.add(p);

    p = new JPanel(new GridLayout(1, thirdRow.length));
    for(int i = 0; i < thirdRow.length; ++i) 
    {
        JButton b = new JButton(thirdRow[i]);
        b.addMouseListener(mouseListener1);
        p.add(b);
    }          
    pfinal.add(p);

    p = new JPanel(new GridLayout(1, fourthRow.length));
    for(int i = 0; i < fourthRow.length; ++i) 
    {
        JButton b = new JButton(fourthRow[i]);
        b.addMouseListener(mouseListener1);
        p.add(b);
    }
    pfinal.add(p);

    p = new JPanel();

    GridBagLayout gridbag = new GridBagLayout();

    p.setLayout(gridbag);
    for (String fifthRow1 : fifthRow) {
        JButton b = new JButton(fifthRow1);
        GridBagConstraints c = new GridBagConstraints();
        if ("Space".equals(fifthRow1)) {
            c.gridwidth = 1;
            c.gridheight = 1;
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0.70;
            c.weighty = 1;
            gridbag.setConstraints(b, c);
        } else {
            c.gridwidth = 1;
            c.gridheight = 1;
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0.10;
            c.weighty = 1;

            gridbag.setConstraints(b, c);
        }
        b.addMouseListener(mouseListener1);
        p.add(b);
    }
      pfinal.add(p);
      search.add(pfinal);
    searchField.getDocument().addDocumentListener(new DocumentListener(){

       @Override
       public void changedUpdate(DocumentEvent arg0){}
       @Override
       public void insertUpdate(DocumentEvent arg0){
           try {
               update(arg0);
           } catch (BadLocationException ex) {
               Exceptions.printStackTrace(ex);
           }
       }

       @Override
       public void removeUpdate(DocumentEvent arg0){
           try {
               update(arg0);
           } catch (BadLocationException ex) {
               Exceptions.printStackTrace(ex);
           }
       }
       @SuppressWarnings("null")

       public void update(DocumentEvent e) throws BadLocationException{
           scrollS = new JPanel();
           scrollS.setLayout(new GridLayout(0,2));
           String output;
           output = searchField.getText();
           if (output.length()>0){
               for(String t:classFinal.keySet()){
                   t = t.toLowerCase();        
                   if(t.startsWith(output.toLowerCase()) || t.equals(output.toLowerCase())){
                       if (classFinal.get(t)!=null){
                           for(String p:classFinal.get(t)){
                               if(isClass(p)){
                                    JLabel scrollRes = new JLabel(p);
                                    scrollRes.setFont(new Font("Monospaced", Font.PLAIN, 13));
                                    scrollRes.setForeground(Color.blue); 
                                    scrollS.add(scrollRes);
                                    scrollRes.addMouseListener(mouseListener2);
                               }
                           }
                       }                         
                   }
               }
           }
           if (search.getComponentCount()>1)
               search.remove(1);
           search.add(new JScrollPane(scrollS),1);
           search.validate();
           search.repaint();

       } 
});
    mouseListener2 = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){

            JLabel jc = (JLabel)e.getSource();
            int caretPos = jEditorPane2.getCaretPosition();
            String className = jc.getText().split("\\.")[jc.getText().split("\\.").length-1];
            
            try {
                jEditorPane2.getDocument().insertString(caretPos, className+".", null);
                classResultsPanel.removeAll();
                classResultsPanel.repaint();
                if (Class.forName(jc.getText()).getFields().length !=0)
                    classResultsPanel.add(returnPanelFields(Class.forName(jc.getText())));
                if (Class.forName(jc.getText()).getDeclaredMethods().length !=0)
                    classResultsPanel.add(returnPanelMethods(Class.forName(jc.getText())));
                jTabbedPane1.setSelectedIndex(4);
            } catch (BadLocationException | ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;
                test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        @Override
        public void mouseExited(MouseEvent e){
            Object source = e.getSource();
            if (source instanceof JLabel){
                JLabel test = (JLabel)source;
                test.setBorder(null);
            }
        }  
     };
    JPanel fanAndVar = new JPanel();
    fanAndVar.setLayout(new GridLayout(0,2));
    fanAndVar.add(returnPanelFavorites());
    fanAndVar.add(returnPanelVariables());
    favAndVarPanel.add(fanAndVar);
    favAndVarPanel.add(returnExceptions());
    reservedWords();  
  }
   
    public static boolean isClass(String className){
 
        boolean exist = true;
        try 
        {
            if(Modifier.isPublic(Class.forName(className).getModifiers()) && Class.forName(className).getDeclaredMethods().length > 0){
                exist = true;
            }
        } 
        catch (NoClassDefFoundError | NoSuchMethodError | ClassNotFoundException ex) 
        {
            exist = false;
        }
        return exist;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        calibrationPane = new javax.swing.JPanel();
        calibrationPanel = new javax.swing.JPanel();
        controllersLabel = new javax.swing.JLabel();
        controllerList = new javax.swing.JComboBox();
        leftClickLabel = new javax.swing.JLabel();
        leftClickCB = new javax.swing.JComboBox();
        runCodeLabel = new javax.swing.JLabel();
        runCodeCB = new javax.swing.JComboBox();
        reservedWordsLabel = new javax.swing.JLabel();
        reservedWordsCB = new javax.swing.JComboBox();
        favoritesLabel = new javax.swing.JLabel();
        favoritesTabCB = new javax.swing.JComboBox();
        searchLabel = new javax.swing.JLabel();
        searchTabCB = new javax.swing.JComboBox();
        classLabel = new javax.swing.JLabel();
        classCB = new javax.swing.JComboBox();
        runJoystickPanel = new javax.swing.JPanel();
        toggleJoystick = new javax.swing.JToggleButton();
        refreshButton = new javax.swing.JButton();
        rwPane = new javax.swing.JPanel();
        search = new javax.swing.JPanel();
        favAndVarPanel = new javax.swing.JPanel();
        classResultsPanel = new javax.swing.JPanel();
        editorWindowPane = new javax.swing.JPanel();
        toolbarEditorPane = new javax.swing.JPanel();
        editorWindow = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(600, 300));
        setLayout(new java.awt.GridLayout(1, 0));

        jInternalFrame1.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(500, 300));
        jInternalFrame1.setVisible(true);
        jInternalFrame1.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(94, 85));

        calibrationPane.setLayout(new java.awt.GridLayout(0, 1));

        calibrationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.calibrationPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 153))); // NOI18N
        calibrationPanel.setLayout(new java.awt.GridLayout(7, 2));

        controllersLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(controllersLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.controllersLabel.text")); // NOI18N
        calibrationPanel.add(controllersLabel);

        controllerList.setMinimumSize(new java.awt.Dimension(100, 100));
        controllerList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                controllerListActionPerformed(evt);
            }
        });
        calibrationPanel.add(controllerList);
        controllerList.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.controllerList.AccessibleContext.accessibleName")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(leftClickLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.leftClickLabel.text")); // NOI18N
        calibrationPanel.add(leftClickLabel);

        calibrationPanel.add(leftClickCB);

        org.openide.awt.Mnemonics.setLocalizedText(runCodeLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.runCodeLabel.text")); // NOI18N
        calibrationPanel.add(runCodeLabel);

        calibrationPanel.add(runCodeCB);

        org.openide.awt.Mnemonics.setLocalizedText(reservedWordsLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.reservedWordsLabel.text")); // NOI18N
        calibrationPanel.add(reservedWordsLabel);

        calibrationPanel.add(reservedWordsCB);

        org.openide.awt.Mnemonics.setLocalizedText(favoritesLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.favoritesLabel.text")); // NOI18N
        calibrationPanel.add(favoritesLabel);

        calibrationPanel.add(favoritesTabCB);

        org.openide.awt.Mnemonics.setLocalizedText(searchLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.searchLabel.text")); // NOI18N
        calibrationPanel.add(searchLabel);

        calibrationPanel.add(searchTabCB);

        org.openide.awt.Mnemonics.setLocalizedText(classLabel, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.classLabel.text")); // NOI18N
        calibrationPanel.add(classLabel);

        calibrationPanel.add(classCB);

        calibrationPane.add(calibrationPanel);
        calibrationPanel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.calibrationPanel.AccessibleContext.accessibleName")); // NOI18N

        runJoystickPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        runJoystickPanel.setLayout(new java.awt.GridBagLayout());

        toggleJoystick.setForeground(new java.awt.Color(0, 0, 204));
        org.openide.awt.Mnemonics.setLocalizedText(toggleJoystick, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.toggleJoystick.text")); // NOI18N
        toggleJoystick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toggleJoystickMouseClicked(evt);
            }
        });
        toggleJoystick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleJoystickActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        runJoystickPanel.add(toggleJoystick, gridBagConstraints);

        refreshButton.setForeground(new java.awt.Color(0, 204, 51));
        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.refreshButton.text")); // NOI18N
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
        });
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        runJoystickPanel.add(refreshButton, gridBagConstraints);

        calibrationPane.add(runJoystickPanel);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.calibrationPane.TabConstraints.tabTitle"), calibrationPane); // NOI18N

        rwPane.setBackground(new java.awt.Color(255, 255, 255));
        rwPane.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        rwPane.setLayout(new java.awt.GridLayout(0, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.rwPane.TabConstraints.tabTitle"), rwPane); // NOI18N

        search.setLayout(new java.awt.GridLayout(2, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.search.TabConstraints.tabTitle"), search); // NOI18N

        favAndVarPanel.setLayout(new java.awt.GridLayout(2, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.favAndVarPanel.TabConstraints.tabTitle"), favAndVarPanel); // NOI18N

        classResultsPanel.setLayout(new java.awt.GridLayout(2, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.classResultsPanel.TabConstraints.tabTitle"), classResultsPanel); // NOI18N

        jInternalFrame1.getContentPane().add(jTabbedPane1);
        jTabbedPane1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        editorWindowPane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        editorWindowPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        toolbarEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbarEditorPane.setLayout(new javax.swing.BoxLayout(toolbarEditorPane, javax.swing.BoxLayout.LINE_AXIS));

        editorWindow.setLayout(new java.awt.GridLayout(2, 1));

        javax.swing.GroupLayout editorWindowPaneLayout = new javax.swing.GroupLayout(editorWindowPane);
        editorWindowPane.setLayout(editorWindowPaneLayout);
        editorWindowPaneLayout.setHorizontalGroup(
            editorWindowPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbarEditorPane, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addComponent(editorWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        editorWindowPaneLayout.setVerticalGroup(
            editorWindowPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editorWindowPaneLayout.createSequentialGroup()
                .addComponent(toolbarEditorPane, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editorWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
        );

        jInternalFrame1.getContentPane().add(editorWindowPane);

        add(jInternalFrame1);
    }// </editor-fold>//GEN-END:initComponents

    private void controllerListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_controllerListActionPerformed

    }//GEN-LAST:event_controllerListActionPerformed

    private void toggleJoystickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleJoystickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggleJoystickActionPerformed

    private void toggleJoystickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toggleJoystickMouseClicked

        if(toggleJoystick.isSelected()){
            String t = controllerList.getSelectedItem().toString();
           
            for (Controller selectedCont: newsticks){
                if(selectedCont.getName().equals(t)){                   
                    try{
                        components = selectedCont.getComponents();
                    }
                    catch(NullPointerException e){
                        System.exit(1);
                    }
                   
                    Xaxis = components[1];
                    Yaxis = components[2];
                    buttons= new HashMap<>();
                    for (Component component : components) {

                        if (component.getName().equalsIgnoreCase("x axis") && component.isAnalog()) {
                            Xaxis = component;
                        }else if (component.getName().equalsIgnoreCase("y axis") && component.isAnalog()) {
                            Yaxis = component;
                        }else if(component.getName().contains("Button")){
                            buttons.put(component.getName(),component);
                        }                       

                    }
//                    for (Component buttonC:buttons){
//                        addToComboBox(buttonC.getName(), reservedWordsCB);
//                    }            

                    try {
                        r = new Robot();
                    } catch (AWTException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            while (isRunningThread) {
                                if(selectedCont.poll()){
                                     mousePos = MouseInfo.getPointerInfo().getLocation();
                                     currX = mousePos.x;
                                     currY = mousePos.y;

                                     float xData = Xaxis.getPollData();
                                     float yData = Yaxis.getPollData();
                                     newX = (int) (xData*20) + currX;
                                     newY = (int) (yData*20) + currY;

                                     if (buttons.get(reservedWordsCB.getSelectedItem().toString()).getPollData()==1.0){
                                         jTabbedPane1.setSelectedComponent(rwPane);
                                         reservedWords();
                                     }
                                     if (buttons.get(leftClickCB.getSelectedItem().toString()).getPollData()==1.0 && !leftButton){
                                         leftButton = true;
                                         r.mousePress(InputEvent.BUTTON1_MASK);
                                     }
                                     if (buttons.get(leftClickCB.getSelectedItem().toString()).getPollData()==0.0 && leftButton){                               
                                         r.mouseRelease(InputEvent.BUTTON1_MASK);
                                         leftButton = false;
                                     }
                                     if (buttons.get(favoritesTabCB.getSelectedItem().toString()).getPollData()==1.0){
                                         jTabbedPane1.setSelectedIndex(3);
                                     }
                                     if (buttons.get(classCB.getSelectedItem().toString()).getPollData()==1.0){
                                         jTabbedPane1.setSelectedIndex(4);
                                     }
                                     if (buttons.get(runCodeCB.getSelectedItem().toString()).getPollData()==1.0){
                                         try {
                                             runCode();
                                         } catch (IOException ex) {
                                             Exceptions.printStackTrace(ex);
                                         }
                                     }
                                     if (buttons.get(searchTabCB.getSelectedItem().toString()).getPollData()==1.0){
                                         jTabbedPane1.setSelectedIndex(2);
                                     }
                                     if (newX != currX || newY != currY) {
                                         mouseGlide(r, currX,currY,newX,newY,10,5);
                                     }

                                  }else{
                                    isRunningThread = false;
                                }
                        }
                        }
                    }).start();
                }
            }
        }
    }//GEN-LAST:event_toggleJoystickMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked
        controllerList.removeAllItems();
        controllerList.repaint();
        refreshComboBoxes();
        ControllerJoystick joy = new ControllerJoystick();
        newsticks = joy.getControllers();

        if (newsticks.isEmpty()){
            addToComboBox("No controllers found!",controllerList);

        }else{
            for (Controller i : newsticks) {
                addToComboBox(i.getName(), controllerList);
            }
        }
    }//GEN-LAST:event_refreshButtonMouseClicked
    public static void mouseGlide(Robot r, int x1, int y1, int x2, int y2, int t, int n) {
      double dx = (x2 - x1) / ((double) n);
        double dy = (y2 - y1) / ((double) n);
        double dt = t / ((double) n);
        for (int step = 1; step <= n; step++) {
            r.delay((int)dt);
            r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
        }
}
   
    public void recursiveSearch(String dirName){
        File dir = new File(dirName);
        File[] fileList =  dir.listFiles();        
        for (File file : fileList){
            if (file.isFile()){
               content.add(file);
            } else if (file.isDirectory()){
                recursiveSearch(file.getAbsolutePath());
            }
        }
    }
    public  void getJarContent(String jarPath) throws IOException{

        JarFile jar = new JarFile(jarPath);
        String entry;
        String []splittedString;
        ArrayList<String> arrayString;
        ArrayList<String> arrayClasses;
        try {
           for (Enumeration<JarEntry> list = jar.entries(); list.hasMoreElements(); ) {        
              entry  = list.nextElement().getName();        
              if (entry.endsWith(".class")){
                      splittedString= entry.replace("/",".").split("\\.");
                      arrayString  = classFinal.get(splittedString[splittedString.length-2]);
                      String packagePath  = entry.replace("/"+splittedString[splittedString.length-2]+".class", "");  
                      arrayClasses = packageFinal.get(packagePath);

                      if (arrayString == null){
                          arrayString = new ArrayList<>();
                          arrayString.add(entry.replace("/",".").replace(".class", ""));
                          classFinal.put(splittedString[splittedString.length-2], arrayString);
                      }else{
                          arrayString.add(entry.replace("/",".").replace(".class", ""));
                          classFinal.put(splittedString[splittedString.length-2], arrayString);
                      }
                      if(arrayClasses==null){
                          arrayClasses = new ArrayList<>();
                          arrayClasses.add(splittedString[splittedString.length-2]);
                          packageFinal.put(packagePath, arrayClasses);
                      }else{
                          arrayClasses.add(splittedString[splittedString.length-2]);
                          packageFinal.put(packagePath, arrayClasses);
                      }


              }
           }
        }
        finally {
           jar.close();
        }

  }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel calibrationPane;
    private javax.swing.JPanel calibrationPanel;
    private javax.swing.JComboBox classCB;
    private javax.swing.JLabel classLabel;
    private javax.swing.JPanel classResultsPanel;
    private javax.swing.JComboBox controllerList;
    private javax.swing.JLabel controllersLabel;
    private javax.swing.JPanel editorWindow;
    protected javax.swing.JPanel editorWindowPane;
    private javax.swing.JPanel favAndVarPanel;
    private javax.swing.JLabel favoritesLabel;
    private javax.swing.JComboBox favoritesTabCB;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox leftClickCB;
    private javax.swing.JLabel leftClickLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JComboBox reservedWordsCB;
    private javax.swing.JLabel reservedWordsLabel;
    private javax.swing.JComboBox runCodeCB;
    private javax.swing.JLabel runCodeLabel;
    private javax.swing.JPanel runJoystickPanel;
    private javax.swing.JPanel rwPane;
    private javax.swing.JPanel search;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JComboBox searchTabCB;
    private javax.swing.JToggleButton toggleJoystick;
    private javax.swing.JPanel toolbarEditorPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }
    
   

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}