/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg.TestWindow;
 
import net.java.games.input.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
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
 private ReservedWords r;
 private List<JLabel> f;
 
 public TestWindowTopComponent() throws IOException{
             r = new ReservedWords("java");
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
    public void initEditor(){
        
                    
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
        

         for(JLabel exp:f){
                   JLabel test = exp;
                   test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                   test.setHorizontalAlignment(SwingConstants.CENTER);
                   test.setBorder(new LineBorder(Color.GRAY, 1, true));
                   test.setFont(new Font("Monospaced", Font.PLAIN, 13));
                   test.setForeground(Color.blue);
                   rwPane.add(test);
                   test.addMouseListener(ml);
                }
        
        
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        rwPane = new javax.swing.JPanel();
        calibrationPane = new javax.swing.JPanel();
        editorWindow = new javax.swing.JPanel();
        outputPane = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(600, 300));
        setLayout(new java.awt.GridLayout(1, 0));

        jInternalFrame1.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        jInternalFrame1.setVisible(true);
        jInternalFrame1.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        rwPane.setBackground(new java.awt.Color(255, 255, 255));
        rwPane.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        rwPane.setLayout(new java.awt.GridLayout(0, 4));
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(TestWindowTopComponent.class, "TestWindowTopComponent.rwPane.TabConstraints.tabTitle"), rwPane); // NOI18N
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


 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel calibrationPane;
    protected javax.swing.JPanel editorWindow;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JTabbedPane jTabbedPane1;
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
