/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetAscenseur;
import java.text.ParseException;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
/**
 *
 * @author aymeric
 */

 public class FormattedTextFieldVerifier extends InputVerifier {
     
     public boolean verify(JComponent input) {
         System.out.print("looool");
         if (input instanceof JFormattedTextField) {
             JFormattedTextField ftf = (JFormattedTextField)input;
             AbstractFormatter formatter = ftf.getFormatter();
             if (formatter != null) {
                 String text = ftf.getText();
                 try {
                      formatter.stringToValue(text);
                      return true;
                  } catch (ParseException pe) {
                      return false;
                  }
              }
          }
          return true;
      }
      public boolean shouldYieldFocus(JComponent input) {
                   System.out.print("pouet");

          return verify(input);
      }
  }

