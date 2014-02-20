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
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
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
    
 private JEditorPane jEditorPane2 = null;
 private final ReservedWords r;
 private final  Map<String, ArrayList<JLabel>>  f;
 private ArrayList<Controller> newsticks;

 private Component[] components;
 public TestWindowTopComponent() throws IOException{
             r = new ReservedWords("test");
             f = r.retList();
             initComponents();
             initEditor();
             initMyComp();
 }
    
 public static String runCode(String s) throws Exception{
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager sjfm = jc.getStandardFileManager(null, null, null);
        File jf = new File("test.java"); //create file in current working directory
        PrintWriter pw = new PrintWriter(jf);
        pw.println("public class test {public static void main(){"+s+"}}");
        
        Iterable fO = sjfm.getJavaFileObjects(jf);
        if(!jc.getTask(null,sjfm,null,null,null,fO).call()) { //compile the code
            throw new Exception("compilation failed");
        }
        URL[] urls = new URL[]{org.openide.util.Utilities.toURI(new File("")).toURL()}; //use current working directory
        URLClassLoader ucl = new URLClassLoader(urls);
        Object o= ucl.loadClass("test").newInstance();
        return (String) o.getClass().getMethod("main").invoke(o);

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
    
    private  void addToComboBox(String msg){
        jComboBox1.addItem(msg);
    }
      public static final int DELAY = 50000; 
    public  void initMyComp() throws IOException { 


        MouseListener ml;
        ml = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JLabel jc = (JLabel)e.getSource();
                 int caretPos = jEditorPane2.getCaretPosition();
                 try {
                     jEditorPane2.getDocument().insertString(caretPos, jc.getText(), null);
                 } catch(BadLocationException ex) {
                 }
                
            }
        };  
        
        jEditorPane2.getDocument().addDocumentListener(new DocumentListener()
        {

            @Override
            public void changedUpdate(DocumentEvent arg0) 
            {

            }
            @Override
            public void insertUpdate(DocumentEvent arg0) 
            {
                try {
                    update(arg0);
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
 
            }
            
            @Override
            public void removeUpdate(DocumentEvent arg0) 
            {

            }
            public void update(DocumentEvent e) throws BadLocationException, Exception{
                Document doc = e.getDocument();
                String output  = doc.getText(0, doc.getLength());
                if (output.endsWith(".")){
                    String sub = "";
                    if(output.length() >= 2){
                        for (int i = output.length()-2; i>=0 ; i--){
//                            sub = sub+output.charAt(i);
//                            System.out.println(output.charAt(i));
//                            if(output.charAt(i) == ' '  || output.charAt(i) == '.'){
//                                sub+=".class.getObject()";
//                                String test = runCode("System.out.println("+sub+");");
//                                Class c = Class.forName(test);
//                                            Method m[] = c.getDeclaredMethods();
//                            for (int j = 0; j < m.length; j++)
//                             System.out.println(m[j].getName()+ " "+m[j].getReturnType()); 

                            }
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

                 
            for ( Map.Entry<String, ArrayList<JLabel>> entry : f.entrySet() ) {
                   String key = entry.getKey();
                   ArrayList<JLabel> value = entry.getValue();
                   JPanel form = new JPanel();
                   form.setLayout(new GridLayout(0,5));
                   TitledBorder title;
                   title = BorderFactory.createTitledBorder(key);
                   form.setBorder(title);
                    for (JLabel test: value) {
                        test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        test.setHorizontalAlignment(SwingConstants.CENTER);
                        test.setBorder(new LineBorder(Color.GRAY, 1, true));
                        test.setFont(new Font("Monospaced", Font.PLAIN, 13));
                        test.setForeground(Color.blue); 
                        test.addMouseListener(ml);
                        form.add(test);
                    }

                   rwPane.add(form);
               
                }
        
        
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
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jComboBox1.setMinimumSize(new java.awt.Dimension(100, 100));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.8;
        jPanel1.add(jComboBox1, gridBagConstraints);
        jComboBox1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jComboBox1.AccessibleContext.accessibleName")); // NOI18N

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 153))); // NOI18N
        calibrationPane.add(jPanel2);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.calibrationPane.TabConstraints.tabTitle"), calibrationPane); // NOI18N

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        jComboBox1.removeAllItems();
        jComboBox1.repaint();
        
    
        ControllerJoystick joy = new ControllerJoystick();
        newsticks = joy.getControllers();
        
        if (newsticks.isEmpty()){
            addToComboBox("No controllers found!");

         }else{             
            for (Controller i : newsticks) {
                addToComboBox(i.getName());
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        
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
                    for (Component component : components) {
                        if (component.getName().equalsIgnoreCase("x axis") && component.isAnalog()) {
                          Xaxis = component;
                          System.out.println("X axis assigned to: Component " + "(" + Xaxis.getName() + ")");
                        }
                        if (component.getName().equalsIgnoreCase("y axis") && component.isAnalog()) {
                            Yaxis = component;
                           System.out.println("Y axis assigned to: Component " + "(" + Yaxis.getName() + ")");
                        }
                    }
                                
                    Point mousePos;
                    int currX;
                    int currY;
                    int newX;
                    int newY;
                    selectedCont.poll();   
                    
                    while (true) {

                       if(selectedCont.poll()){
                            mousePos = MouseInfo.getPointerInfo().getLocation();
                            currX = mousePos.x;
                            currY = mousePos.y;

                            float xData = Xaxis.getPollData();
                            float yData = Yaxis.getPollData();
                            newX = (int) (xData*20) + currX;
                            newY = (int) (yData*20) + currY;



                            // Checks to see if new position is not the old position so the mouse
                            // isn't taken over by this program
                            if (newX != currX || newY != currY) {
                                mouseGlide(currX,currY,newX,newY,10,5);

                            }
                         }else{
                           break;
                       }
                    }
                }
            }
        }
    }//GEN-LAST:event_jToggleButton1MouseClicked
   public static void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
    try {
        Robot r = new Robot();
        double dx = (x2 - x1) / ((double) n);
        double dy = (y2 - y1) / ((double) n);
        double dt = t / ((double) n);
        for (int step = 1; step <= n; step++) {
            r.delay((int)dt);
            r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
        }
    } catch (AWTException e) {
    }
}
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel calibrationPane;
    protected javax.swing.JPanel editorWindow;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel outputPane;
    private javax.swing.JPanel rwPane;
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
