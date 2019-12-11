/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javaapplication1;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;

/**
*
* @author PC
*/
public class Dashboard extends javax.swing.JFrame {

	/**
	* Creates new form Dashboard
	*/
	SocketHelper sh;
	public Dashboard(SocketHelper sh) {
		initComponents();
		this.sh = sh;
	}

	/**
	* This method is called from within the constructor to initialize the form.
	* WARNING: Do NOT modify this code. The content of this method is always
	* regenerated by the Form Editor.
	*/
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Manual Commands:");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cd", "pwd", "ls", "mkdir", "rmdir", "mv", "cp", "rnm", "download", "upload", " " }));

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Current Working Directory:");

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Command Output\n");
        jScrollPane2.setViewportView(jTextArea1);

        jLabel3.setText("Logged in as");

        jButton2.setText("Clear Output");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Store downloaded files at:");

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jButton3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
String cwd = "";	
    private void updateCwd(String cwd)
	{
            this.cwd = cwd;
		jLabel2.setText("Current Working Directory: " + cwd);
	}
	private void getPwd()
	{
		sh.writeLine("pwd");        
		updateCwd(sh.readLine());
	}
	DefaultListModel<String> model = new DefaultListModel<>();
		private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
		
                    DefaultCaret caret = (DefaultCaret) jTextArea1.getCaret();
caret.setUpdatePolicy(ALWAYS_UPDATE);	
                    jList1.setModel(model);
			sh.writeLine("/e");        
			jLabel3.setText("Logged in as " + sh.readLine());
			getPwd();
			jTextArea1.setEditable(false);
			Thread thread = new Thread(() -> {
                            while (true)
                            {
				String input;
				boolean _listUpdate = false;
				while ((input = sh.readLine()) != null && input.length() > 0)
				{
					if (_listUpdate)
					{
						if (input.equals("#list/"))
						{
							
							_listUpdate = false;
							if (model.getSize() > 0)
							jList1.ensureIndexIsVisible(model.getSize() - 1);
							continue;
						}
						
						model.addElement(input);
						
						
					}
                                        else if (input.equals("#upload"))
                                        {
                                          
                                            try {
                                                passFile(targetUpload, sh);
                                            } catch (IOException ex) {
                                                appendLine("[ERROR] An error occurred during file transmission. The uploaded file might be corrupt.");
                                            }
                                        }
					else if (input.startsWith("#cwd:")) {
						updateCwd(input.replaceAll("^#cwd:", ""));
					}
					else if (input.equals("/#list"))
					{
						model.removeAllElements();
                                                if (!cwd.equals("/")) model.addElement("..");
						_listUpdate = true;
						
					}
                                        else if (input.equals("/#download"))
                                        {
                                            FileOutputStream stream = null;
                                            String file_name = "";
                                            if (!checkStoreFolder())
                                            {
                                                GUI.msgBox("The supplied storage directory does not exist. No files were saved.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                            }
                                            else{
                                             file_name = getStore() + "\\"+sh.readLine();
                                            try {
                                                 stream = new FileOutputStream(file_name);
                                            } catch (IOException ex) {
                                                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            }
                                            try {
                                            DataInputStream is = sh.getInputStream();
                                             byte[] ioBuf = new byte[1]; 
                                             int bytesRead;
                                                while ((bytesRead = is.read(ioBuf)) != -1)
                                                {
                                                    if (stream != null)
                                                    {
                                                        stream.write(ioBuf);
                                                    }
                                                }
                                            } catch (IOException ex) {
                                                 appendLine("$ An error occurred during transmission; the file might be corrupt.");
                                                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            if (stream != null) { try {
                                                stream.flush();
                                                stream.close();
                                                appendLine("[INFO] File downloaded at '"+file_name+"'");
                                                
                                                } catch (IOException ex) {
                                                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                                                     appendLine("[ERROR] An error occurred during saving the file. Ensure you have permissions and try again.");
                                                }
 }
                                        }
					else
					{
						appendLine(input);
					}
					
				}
                                try {
                                    Thread.sleep(1000); //wait for reconnect
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
			});
			thread.start();
                        refreshCwd();
                
		}//GEN-LAST:event_formWindowOpened
		private String getStore()
                {
                    return jTextField1.getText();
                }
                private void appendLine(String text)
                {
                    jTextArea1.append(text+"\n");
                }
                
    private void passFile(String filePath, SocketHelper sh) throws IOException{
        DataOutputStream os = sh.getOutputStream();
        File target = new File(filePath);
        if (target.exists())
        {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(target));
            byte[] ioBuf = new byte[1];       
            int bytesRead;
            while (in.read(ioBuf) != -1){
               os.write(ioBuf);
            }
           sh.client.close();
           sendCommand("ls");
        }
    }
    String targetUpload = "";
                private void sendCommand(String command)
		{
                   
                    if (command.startsWith("upload "))
                    {
                        targetUpload = fileChooser(false);
                    }
                    if (command.startsWith("upload ") && targetUpload.length() == 0) return;
                            
                        
                    appendLine("$ " + command);
			sh.writeLine(command);
		}     
                private void refreshCwd()
                {
                    sendCommand("ls");
                
                }
                
        private String filterCommand(String input, String command)
        {
            return input.replaceAll("^"+command+" ", "");
        }
		private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
			refreshCwd();
		}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextArea1.setText("Command Output\n");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sendCommand(jComboBox1.getSelectedItem().toString());
    }//GEN-LAST:event_jButton1ActionPerformed
    private String filterItem(String item)
    {
        return item.replaceAll("^\\[(F|D)\\] ", "");
    }
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (jList1.getSelectedIndex() >= 0)
        {
            String item = model.getElementAt(jList1.getSelectedIndex());
            String command = "cd";
            if (item.startsWith("[F]")) command = "download";
            sendCommand(command + " " +filterItem(item));
        }
    }//GEN-LAST:event_jList1MouseClicked
    private boolean checkStoreFolder()
    {
        File _file = new File(getStore());
        return (_file.exists());
    }
    private String fileChooser(boolean folderOnly)
    {
        JFileChooser fileChooser = new JFileChooser();
        if (folderOnly)
            fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
int result = fileChooser.showOpenDialog(this);

if (result == JFileChooser.APPROVE_OPTION) {
    File _file = fileChooser.getSelectedFile();
        if(_file.exists())
        {
            try {
                return _file.getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
return "";
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
jTextField1.setText(fileChooser(true));
    }//GEN-LAST:event_jButton4ActionPerformed

		/**
	* @param args the command line arguments
	*/
		public static void main(String args[]) {
			/* Set the Nimbus look and feel */
			//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
			/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		* For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		*/
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			}
			//</editor-fold>

			/* Create and display the form */
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
				}
			});
		}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
	}
