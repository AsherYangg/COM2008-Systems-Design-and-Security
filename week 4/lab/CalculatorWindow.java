import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class CalculatorWindow extends JFrame {

    enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private JTextField outpuTextField;
    private Stack<Double> operandStack;
    private Operator currentOperator;
    private boolean newInput;

    public CalculatorWindow(String text){
        super(text);
        operandStack = new Stack<>();
        currentOperator = null;
        newInput = true;
    
        outpuTextField = new JTextField();
        outpuTextField.setEditable(false);
        outpuTextField.setMaximumSize(new Dimension(225,40));
        outpuTextField.setFont(new Font("Monospaced", Font.BOLD, 20));
        outpuTextField.setDisabledTextColor(new Color(0,0,0));
        outpuTextField.setMargin(new Insets(0,5,0,0));
    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,4));
    
        for (int i = 0 ; i <= 9 ; i++){
            addButton(panel, String.valueOf(i));
        }

        addButton(panel, "+");
        addButton(panel, "-");
        addButton(panel, "*");
        addButton(panel, "/");
        addButton(panel, "=");
        addButton(panel, "C");
    
        this.add(outpuTextField, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER); // Add this line
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setMinimumSize(new Dimension(300, 300)); // Adjust based on your needs

    }

    private void addButton(JPanel panel, String label){
        System.out.println("addButton method is called with label: " + label);
        JButton button = new JButton(label);
        button.setFont(new Font("Monospaced", Font.BOLD, 22));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(label);
            }
        });
        panel.add(button); // This line is crucial.
        System.out.println("addButton method is finished");
    }
    

        private void handleButtonClick(String label){
            if(label.matches("[0-9]")){
                if(newInput){
                    outpuTextField.setText(label);
                    newInput = false;
                    operandStack.push(Double.parseDouble(label));
                }else{
                    newInput = false;
                    outpuTextField.setText(outpuTextField.getText() + label);
                    double currentNumber = operandStack.pop();
                    // Handle appending digits to the current number
                    operandStack.push(currentNumber * 10 + Double.parseDouble(label));
                }
        }else if(label.equals("C")){
            //Handle clear button
            operandStack.clear();
            currentOperator = null;
            outpuTextField.setText("");
            newInput = true;
    }else if (label.equals("=")){
        if(!newInput && currentOperator != null){
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            double result = performanceOperation( operand1, operand2,currentOperator);
            outpuTextField.setText(String.valueOf(result));
            operandStack.push(result);
            currentOperator = null;
            newInput = true;
        }
    }else{
        // handle operator selection
        Operator operator = getOperatorFromLabel(label);

        //if not a new input, perform the previous operation

        if(!newInput && currentOperator != null){
            handleButtonClick("=");
        }
        currentOperator = operator;
        newInput = true;
    }

    }

    private Operator getOperatorFromLabel(String label){
        switch (label){
            case "+":
                return Operator.ADD;
            case "-":
                return Operator.SUBTRACT;
            case "*":
                return Operator.MULTIPLY;
            case "/":
                return Operator.DIVIDE;
            default:
                throw new IllegalArgumentException("Invalid operator: " + label);
        }
    }

    private double performanceOperation(double operand1, double operand2, Operator operator){
        switch (operator){
            case ADD:
                return operand1 + operand2;
            case SUBTRACT:
                return operand1 - operand2;
            case MULTIPLY:
                return operand1 * operand2;
            case DIVIDE:
                if (operand2 != 0){
                    return operand1 / operand2;
                }else{
                    JOptionPane.showMessageDialog(this,"Division by zero" ,"Error", JOptionPane.ERROR_MESSAGE);
                    return Double.NaN;
                }
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
