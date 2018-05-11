package Lab8.CalculatorJNLP;

import java.awt.EventQueue;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalculatorFrame {

	private double calculatorResult = 0;
	
	private JFrame frmCalculator;
	private JTextField txtResult;
	private JFormattedTextField txtInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorFrame window = new CalculatorFrame();
					window.frmCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.setTitle("Calculator");
		frmCalculator.setBounds(100, 100, 300, 207);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(null);
		
		txtResult = new JTextField();
		txtResult.setHorizontalAlignment(SwingConstants.RIGHT);
		txtResult.setEditable(false);
		txtResult.setText("0");
		txtResult.setBounds(10, 11, 262, 20);
		frmCalculator.getContentPane().add(txtResult);
		txtResult.setColumns(10);
		
		
		
		
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numberFormatter.setValueClass(Float.class); //optional, ensures you will always get a long value
		numberFormatter.setAllowsInvalid(false); //this is the key!!\
		numberFormatter.setMinimum(0f);
		txtInput = new JFormattedTextField(numberFormatter);
		txtInput.setBounds(10, 62, 262, 20);
		frmCalculator.getContentPane().add(txtInput);
		txtInput.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCalculatorResult(calculatorResult + readInputValue());
			}
		});
		btnAdd.setBounds(10, 93, 120, 23);
		frmCalculator.getContentPane().add(btnAdd);
		
		JButton btnSubstract = new JButton("Substract");
		btnSubstract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCalculatorResult(calculatorResult - readInputValue());
			}
		});
		btnSubstract.setBounds(10, 127, 120, 23);
		frmCalculator.getContentPane().add(btnSubstract);
		
		JButton btnBtnmultiply = new JButton("Multiply");
		btnBtnmultiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCalculatorResult(calculatorResult * readInputValue());
			}
		});
		btnBtnmultiply.setBounds(151, 93, 120, 23);
		frmCalculator.getContentPane().add(btnBtnmultiply);
		
		JButton btnDivide = new JButton("Divide");
		btnDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float v = readInputValue();
				if(v == 0) return;
				
				setCalculatorResult(calculatorResult / v);
			}
		});
		btnDivide.setBounds(151, 127, 120, 23);
		frmCalculator.getContentPane().add(btnDivide);
	}
	
	private float readInputValue() {
		Object value = txtInput.getValue();
		
		return value == null ? 0 : (Float)value;
	}
	
	private void setCalculatorResult(double newValue) {
		calculatorResult = newValue;
		
		txtResult.setText(Double.toString(calculatorResult));
	}
}
