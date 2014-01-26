/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.TestWindow;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;

@MimeRegistrations({
    @MimeRegistration(mimeType="text/html", service=CompletionProvider.class),
    @MimeRegistration(mimeType="text/x-java", service=CompletionProvider.class)
})
public class DemoCompletionProvider implements CompletionProvider {

    @Override
    public CompletionTask createTask(int i, JTextComponent jTextComponent) {
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet resultSet, Document doc, int caretOffset) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    @Override
    public int getAutoQueryTypes(JTextComponent jTextComponent, String string) {
        return 0;
    }
    
}