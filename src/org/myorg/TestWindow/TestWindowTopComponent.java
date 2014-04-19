/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.TestWindow;
 
import java.awt.AWTException;
import net.java.games.input.*;
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
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.editor.BaseDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.NbDocument;
import org.openide.util.NbBundle.Messages;
import org.netbeans.editor.Utilities;
import org.openide.util.Exceptions;
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

    private static  MouseListener ml;
    private JEditorPane jEditorPane2 = null;
    private final ReservedWords r;
    private final  Map<String, ArrayList<JLabel>>  f;
    private ArrayList<Controller> newsticks;
    private   Set<File> content  = new HashSet<File>();
    private   Map<String, ArrayList<String>> classFinal = new HashMap<String, ArrayList<String>>();
    private  static Map<String, ArrayList<String>> packageFinal = new HashMap<String, ArrayList<String>>();
    private static final  String[] classFavoritesInit = {"java.lang.String","java.lang.System","java.lang.Exception"
                                               ,"java.util.ArrayList","java.util.HashMap","java.lang.Object"
                                               ,"java.lang.Thread","java.lang.Class","java.util.Date","java.util.Iterator"};
    private static ArrayList<String> classFavorites = new ArrayList<String>();
    private Component[] components;
    private boolean period = false;
    private final String firstRow[] = {"1","2","3","4","5","6","7","8","9","0","-","+","Backspace"};//BackSpace
    private final String secondRow[] = {"Q","W","E","R","T","Y","U","I","O","P","[","]","\\"};
    private final String thirdRow[] = {"A","S","D","F","G","H","J","K","L",":","\"","Enter"};
    private final String fourthRow[] = {"Shift","Z","X","C","V","B","N","M",",",".","?","   ^" };
    private final String fifthRow[]={"Space" ,"<" ,">" };
    private JTextField searchField;
    private boolean upperCase = true;
    private JPanel scrollS;
    private JPopupMenu popupPackage;
    private boolean leftButton = false;
    
    public TestWindowTopComponent() throws IOException{
            r = new ReservedWords("test");
            f = r.retList();
            
            String loc = System.getProperty("sun.boot.class.path");
            classFavorites.addAll(Arrays.asList(classFavoritesInit));
            for (String path:loc.split(";")){
                 File test = new File(path);
                 if (test.isDirectory() && test.exists())
                     recursiveSearch(path);
                 else
                     content.add(test);         
            }
            for (File file: content) {
              if (file.getPath().endsWith(".jar") && file.exists()){
                  getJarContent(file.getPath().toString());
              }
            }

           
             initComponents();
             initEditor();
             initMyComp();
    }

    public final void initEditor(){
        
                    
        jEditorPane2 = new JEditorPane();
        
    
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
    }
    
    private  void addToComboBox(String msg, JComboBox comboBox){
        comboBox.addItem(msg);
    }
    
    private JPanel returnPanelFavorites (){
        
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(10,0));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Favorites");
       form.setBorder(title); 
       
       if (classFavorites.isEmpty())
           return null;
       
       for (String classF: classFavorites){
            JLabel methodLabel = new JLabel(classF);
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue); 
            methodLabel.addMouseListener(ml);
            form.add(methodLabel);
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
       
       if (fields.length == 0)
            return null;
       
       for (Field field:fields)
       {
            JLabel methodLabel = new JLabel(field.getName());
            methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
            methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
            methodLabel.setForeground(Color.blue); 
            methodLabel.addMouseListener(ml);
            form.add(methodLabel);
       }
        return form;       
        
    }
    
    private JPanel returnPanelMethods(Class newClass){
        
       JPanel form = new JPanel();
       form.setLayout(new GridLayout(0,5));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Methods");
       form.setBorder(title); 
       Method[] methods = newClass.getMethods();
       
       if (methods.length == 0)
           return null;
       
       for (Method method:methods)
       {
         JLabel methodLabel = new JLabel(method.getName());
         methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
         methodLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
         methodLabel.setForeground(Color.blue); 
         methodLabel.addMouseListener(ml);
         form.add(methodLabel);


       }
        return form;
    }
    private void reservedWords(){
          for ( Map.Entry<String, ArrayList<JLabel>> entry : f.entrySet() ) {
               String key = entry.getKey();
               ArrayList<JLabel> value = entry.getValue();
               JPanel form = new JPanel();
               form.setLayout(new GridLayout(0,5));
               TitledBorder title;
               title = BorderFactory.createTitledBorder(key);
               form.setBorder(title);
                for (JLabel test: value) {
                    test.setHorizontalAlignment(SwingConstants.CENTER);
                    test.setFont(new Font("Monospaced", Font.PLAIN, 13));
                    test.setForeground(Color.blue); 
                    test.addMouseListener(ml);
                    form.add(test);
                }

               rwPane.add(form);

            }
    }
    public  void initMyComp() throws IOException { 
        
        ml = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Object source = e.getSource();
                if (source instanceof JLabel){
                    JLabel jc = (JLabel)e.getSource();
                     int caretPos = jEditorPane2.getCaretPosition();
                     try {
                         jEditorPane2.getDocument().insertString(caretPos, jc.getText().split("\\.")[jc.getText().split("\\.").length-1], null);
                     } catch(BadLocationException ex) {
                     }                
                }else if(source instanceof JButton){
                     JButton jc = (JButton)e.getSource();
                     searchField.getCaret().setBlinkRate(500);
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
                     } catch(BadLocationException ex) {
                     } catch (IllegalArgumentException ex){                         
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
         jEditorPane2.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent Me){

                }
            });
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
        
        jEditorPane2.getDocument().addDocumentListener(new DocumentListener()
        {

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
            
            @Override
            public void removeUpdate(DocumentEvent arg0){ }
            @SuppressWarnings("null")
            public void update(DocumentEvent e) throws BadLocationException, Exception{
                
                Document doc = e.getDocument();                     
                String output  = doc.getText(0, jEditorPane2.getCaretPosition());           
        
                if (period){
                   try{                       
                        output = output.substring(output.lastIndexOf("\n")+1);
                        output = output.substring(output.lastIndexOf(" ")+1);
                        Class newClass = null;
                        String test = output.split("\\.")[0];
                        ArrayList<String> temp = classFinal.get(test);
                        if (temp!= null){
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
                                for (String s:output.split("\\."))
                                    System.out.println(s+" " +output.split("\\.").length);
                                if (output.split("\\.").length > 1){
                                    newClass = Class.forName(newClass.getField(output.split("\\.")[1]).getType().getCanonicalName());
                                   
                                }
                                    
//                            }
                            rwPane.removeAll();
                            rwPane.repaint();

                            if (newClass.getFields().length !=0)
                                rwPane.add(returnPanelFields(newClass));
                            if (newClass.getMethods().length !=0)
                                rwPane.add(returnPanelMethods(newClass));
           
                        }
                   }catch( ArrayIndexOutOfBoundsException exp){
                   }catch(NullPointerException exp){
                   }catch(ClassNotFoundException exp){
                       
                   }catch (NoSuchFieldException exp){
                       
                   }
                }
            }
        });
        jToggleButton1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
               if(ev.getStateChange()==ItemEvent.SELECTED){
                 jToggleButton1.setText("Stop");
                 jToggleButton1.setForeground(Color.red);
  
               } else if(ev.getStateChange()==ItemEvent.DESELECTED){
                 jToggleButton1.setText("Start");
                 jToggleButton1.setForeground(Color.blue);
               }
            }
         });
    
       favorites.add(returnPanelFavorites());
        
       reservedWords();

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
              b.addMouseListener(ml);
              p.add(b);
          }
          pfinal.add(p);
          
          p = new JPanel(new GridLayout(1, secondRow.length));
          for(int i = 0; i < secondRow.length; ++i) 
          {
              JButton b = new JButton(secondRow[i]);
              b.addMouseListener(ml);
              p.add(b);
          }
          pfinal.add(p);
          
          p = new JPanel(new GridLayout(1, thirdRow.length));
          for(int i = 0; i < thirdRow.length; ++i) 
          {
              JButton b = new JButton(thirdRow[i]);
              b.addMouseListener(ml);
              p.add(b);
          }          
          pfinal.add(p);
          
          p = new JPanel(new GridLayout(1, fourthRow.length));
          for(int i = 0; i < fourthRow.length; ++i) 
          {
              JButton b = new JButton(fourthRow[i]);
              b.addMouseListener(ml);
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
            b.addMouseListener(ml);
            p.add(b);
        }
          pfinal.add(p);
          search.add(pfinal);
          
        jComboBox1.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String item = (String)e.getItem();
                
                if (e.getStateChange() == ItemEvent.SELECTED)
                {System.out.println(item);
                   ArrayList <Component> buttons= new ArrayList<Component>();
                   reservedWordsCB.removeAll();
                   reservedWordsCB.repaint();
                   
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
                        for (Component buttonC:buttons){
                            addToComboBox(buttonC.getName(), reservedWordsCB);
                            System.out.println(buttonC.getName());
                        }            
                    }
                   }
                }
                else
                {
                 
                }  
            }
        });
         searchField.getDocument().addDocumentListener(new DocumentListener()
        {

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
                if (output.length()>0)
                    for(String t:classFinal.keySet()){
                        t=t.toLowerCase();
                        output=output.toLowerCase();
                        if(t.startsWith(output) || t.equals(output)){
                            if (classFinal.get(Character.toUpperCase(t.charAt(0))+t.substring(1))!=null){
                                for(String p:classFinal.get(Character.toUpperCase(t.charAt(0))+t.substring(1))){
                                    if(isClass(p)){
                                         JLabel scrollRes = new JLabel(p);
                                         scrollRes.setFont(new Font("Monospaced", Font.PLAIN, 13));
                                         scrollRes.setForeground(Color.blue); 
                                         scrollS.add(scrollRes);
                                         scrollRes.addMouseListener(ml);
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
  
  }
   
    public static boolean isClass(String className){
        boolean exist = true;
        try 
        {
            Class.forName(className);
        } 
        catch (ClassNotFoundException e) 
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
        rwPane = new javax.swing.JPanel();
        calibrationPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        reservedWordsCB = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        ClassCB = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        searchTabCB = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        favoritesTabCB = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        favorites = new javax.swing.JPanel();
        search = new javax.swing.JPanel();
        editorWindow = new javax.swing.JPanel();
        outputPane = new javax.swing.JPanel();

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

        rwPane.setBackground(new java.awt.Color(255, 255, 255));
        rwPane.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        rwPane.setLayout(new java.awt.GridLayout(0, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.rwPane.TabConstraints.tabTitle"), rwPane); // NOI18N

        calibrationPane.setLayout(new java.awt.GridLayout(0, 1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 153))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(6, 2));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel6.text")); // NOI18N
        jPanel2.add(jLabel6);

        jComboBox1.setMinimumSize(new java.awt.Dimension(100, 100));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox1);
        jComboBox1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jComboBox1.AccessibleContext.accessibleName")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel1.text")); // NOI18N
        jPanel2.add(jLabel1);

        reservedWordsCB.setSelectedIndex(-1);
        jPanel2.add(reservedWordsCB);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel2.text")); // NOI18N
        jPanel2.add(jLabel2);

        ClassCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ClassCB.setSelectedIndex(-1);
        jPanel2.add(ClassCB);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel3.text")); // NOI18N
        jPanel2.add(jLabel3);

        searchTabCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchTabCB.setSelectedIndex(-1);
        jPanel2.add(searchTabCB);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel4.text")); // NOI18N
        jPanel2.add(jLabel4);

        favoritesTabCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        favoritesTabCB.setSelectedIndex(-1);
        jPanel2.add(favoritesTabCB);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jLabel5.text")); // NOI18N
        jPanel2.add(jLabel5);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox6.setSelectedIndex(-1);
        jPanel2.add(jComboBox6);

        calibrationPane.add(jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jPanel2.AccessibleContext.accessibleName")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jToggleButton1.setForeground(new java.awt.Color(0, 0, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jToggleButton1, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jToggleButton1.text")); // NOI18N
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
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
        jPanel1.add(jToggleButton1, gridBagConstraints);

        jButton1.setForeground(new java.awt.Color(0, 204, 51));
        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jButton1.text")); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
        jPanel1.add(jButton1, gridBagConstraints);

        calibrationPane.add(jPanel1);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.calibrationPane.TabConstraints.tabTitle"), calibrationPane); // NOI18N

        favorites.setLayout(new java.awt.GridLayout(0, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.favorites.TabConstraints.tabTitle"), favorites); // NOI18N

        search.setLayout(new java.awt.GridLayout(2, 1));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.search.TabConstraints.tabTitle"), search); // NOI18N

        jInternalFrame1.getContentPane().add(jTabbedPane1);
        jTabbedPane1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        editorWindow.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        editorWindow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editorWindow.setLayout(new java.awt.GridLayout(0, 1));

        outputPane.setBackground(new java.awt.Color(255, 255, 255));
        outputPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        outputPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        outputPane.setLayout(new java.awt.GridLayout(0, 1));
        editorWindow.add(outputPane);

        jInternalFrame1.getContentPane().add(editorWindow);

        add(jInternalFrame1);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        Map <String, Component> buttons;
        if(jToggleButton1.isSelected()){
            String t = jComboBox1.getSelectedItem().toString();
           
            for (Controller selectedCont: newsticks){
                if(selectedCont.getName().equals(t)){
                    
                    try{
                        components = selectedCont.getComponents();
                    }
                    catch(NullPointerException e){
                        System.exit(1);
                    }
                   
                   Component Xaxis = components[1];
                   Component Yaxis = components[2];
                   buttons= new HashMap<String,Component>();
                    for (Component component : components) {
       
                        if (component.getName().equalsIgnoreCase("x axis") && component.isAnalog()) {
                          Xaxis = component;
                        }else if (component.getName().equalsIgnoreCase("y axis") && component.isAnalog()) {
                            Yaxis = component;
                        }else if(component.getName().contains("Button")){
                            buttons.put(component.getName().toString(),component);
                        }                       

                    }
//                    for (Component buttonC:buttons){
//                        addToComboBox(buttonC.getName(), reservedWordsCB);
//                    }            
                    Point mousePos;
                    int currX;
                    int currY;
                    int newX;
                    int newY;
                    Robot r = null;
                    selectedCont.poll();   
                    try {
                        r = new Robot();
                    } catch (AWTException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    while (true) {

                       if(selectedCont.poll()){
                            mousePos = MouseInfo.getPointerInfo().getLocation();
                            currX = mousePos.x;
                            currY = mousePos.y;

                            float xData = Xaxis.getPollData();
                            float yData = Yaxis.getPollData();
                            newX = (int) (xData*20) + currX;
                            newY = (int) (yData*20) + currY;
                          
                            if (buttons.get(reservedWordsCB.getSelectedItem().toString()).getPollData()==1.0 && !leftButton){
                                leftButton = true;
                                r.mousePress(InputEvent.BUTTON1_MASK);
                                jTabbedPane1.setSelectedComponent(rwPane);
                            }
                             if (buttons.get(reservedWordsCB.getSelectedItem().toString()).getPollData()==0.0 && leftButton){                               
                                r.mouseRelease(InputEvent.BUTTON1_MASK);
                                leftButton = false;
                            }
                            if (newX != currX || newY != currY) {
                                mouseGlide(r, currX,currY,newX,newY,10,5);

                            }
                         }else{
                           break;
                       }
                    }
                }
            }
        }
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        jComboBox1.removeAllItems();
        jComboBox1.repaint();
        reservedWordsCB.removeAllItems();
        reservedWordsCB.repaint();
        ControllerJoystick joy = new ControllerJoystick();
        newsticks = joy.getControllers();

        if (newsticks.isEmpty()){
            addToComboBox("No controllers found!",jComboBox1);

        }else{
            for (Controller i : newsticks) {
                addToComboBox(i.getName(),jComboBox1);
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked
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
    ArrayList arrayString;
    ArrayList arrayClasses;
    try {
       for (Enumeration<JarEntry> list = jar.entries(); list.hasMoreElements(); ) {        
          entry  = list.nextElement().getName();        
          if (entry.endsWith(".class")){
            
                  splittedString= entry.replace("/",".").split("\\.");

                  arrayString  = classFinal.get(splittedString[splittedString.length-2]);
                  String packagePath  = entry.replace("/"+splittedString[splittedString.length-2]+".class", "");  
                  arrayClasses = packageFinal.get(packagePath);

                  if (arrayString == null){
                      arrayString = new ArrayList<String>();
                      arrayString.add(entry.replace("/",".").replace(".class", ""));
                      classFinal.put(splittedString[splittedString.length-2], arrayString);
                  }else{
                      arrayString.add(entry.replace("/",".").replace(".class", ""));
                      classFinal.put(splittedString[splittedString.length-2], arrayString);
                  }
                  if(arrayClasses==null){
                      arrayClasses = new ArrayList<String>();
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
    private javax.swing.JComboBox ClassCB;
    private javax.swing.JPanel calibrationPane;
    protected javax.swing.JPanel editorWindow;
    private javax.swing.JPanel favorites;
    private javax.swing.JComboBox favoritesTabCB;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel outputPane;
    private javax.swing.JComboBox reservedWordsCB;
    private javax.swing.JPanel rwPane;
    private javax.swing.JPanel search;
    private javax.swing.JComboBox searchTabCB;
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