import javax.swing.*;

public class Main {
public static void main(String[] args) {
    final String text = "Calculator";
    SwingUtilities.invokeLater(new Runnable(){
        @Override
        public void run() {
            final CalculatorWindow window = new CalculatorWindow(text);
            window.setVisible(true);
        }
        });
    }
    
}