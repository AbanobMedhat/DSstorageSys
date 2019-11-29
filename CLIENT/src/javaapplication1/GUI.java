/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class GUI {
        public static void msgBox(String message, String title, int icon) {
         JOptionPane.showMessageDialog(null, 
                              message, 
                              title, icon);
    }
}
