package swing;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;


public class Calculator implements ActionListener{

	JFrame jFrame;
	JPanel buttonJPanel;
	JTextField jHint, jResult, jMemory, jTextField; 
	JLabel jLabel1, jLabel2, jLabel3;

	int calcOperation = 0;
	int currentCalc;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Calculator();
			}
		});


	}

	public Calculator() {

		initSystemLookAndFeel("Nimbus", 2); 

		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(new GridBagLayout());
		jFrame.setTitle("Calculator");
		jFrame.setSize(300,400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setResizable(false);

		jHint = new JTextField();
		jHint.setHorizontalAlignment(JTextField.RIGHT);
		jHint.setEditable(false);
		jHint.setFont(new Font("Courier New", Font.PLAIN, 8));
		jHint.setText("0");

		jLabel1 = new JLabel();
		jLabel1.setHorizontalAlignment(JTextField.LEFT);
		jLabel1.setFont(new Font("Courier New", Font.PLAIN, 8));
		jLabel1.setText("OPERATION:");


		jLabel2 = new JLabel();
		jLabel2.setHorizontalAlignment(JTextField.LEFT);
		jLabel2.setFont(new Font("Courier New", Font.PLAIN, 8));
		jLabel2.setText("RESULT:");

		jResult = new JTextField();
		jResult.setHorizontalAlignment(JTextField.RIGHT);
		jResult.setEditable(false);
		jResult.setFont(new Font("Courier New", Font.PLAIN, 8));
		jResult.setText("0");


		jMemory = new JTextField();
		jMemory.setHorizontalAlignment(JTextField.RIGHT);
		jMemory.setEditable(false);
		jMemory.setFont(new Font("Courier New", Font.PLAIN, 8));
		jMemory.setText("0");

		jLabel3 = new JLabel();
		jLabel3.setHorizontalAlignment(JTextField.LEFT);
		jLabel3.setFont(new Font("Courier New", Font.PLAIN, 8));
		jLabel3.setText("MEMORY:");

		jTextField = new JTextField();
		jTextField.setHorizontalAlignment(JTextField.RIGHT);
		jTextField.setEditable(false);
		jTextField.setFont(new Font("Courier New", Font.BOLD, 16));
		jTextField.setText("0");


		//Container content = jFrame.getContentPane();
		jFrame.setLayout(new GridLayout(0, 1));

		buttonJPanel = new JPanel();
		buttonJPanel.setLayout(new GridLayout(5,4));

		String[] buttonLabels = {
				"M+", "M-", "MC", "MR",
				"7", "8", "9","÷",
				"4", "5", "6", "×",
				"1", "2", "3", "-",
				"±", "0", ".", "+"
		};

		for (int i = 0; i < buttonLabels.length; i++) {
			String elem = buttonLabels[i];

			if(elem.matches("[0-9\\.]")) {
				addNumberButton(buttonJPanel, elem);
			} else {
				addActionButton(buttonJPanel, elem);
			}
		}




		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = (new Insets(4,2,1,2));
		jLabel1.setBorder(new EmptyBorder(0,2,0,0));
		pane.add(jLabel1, c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = (new Insets(4,2,1,2));
		pane.add(jHint, c);	

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = (new Insets(4,2,1,2));
		jLabel2.setBorder(new EmptyBorder(0,2,0,0));
		pane.add(jLabel2, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = (new Insets(4,2,1,2));
		pane.add(jResult, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = (new Insets(4,2,1,2));
		jLabel3.setBorder(new EmptyBorder(0,2,0,0));
		pane.add(jLabel3, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.insets = (new Insets(4,2,1,2));
		pane.add(jMemory, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.insets = (new Insets(2,2,4,2));
		pane.add(jTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 120;
		c.ipady = 150;
		c.gridwidth = 3;
		c.insets = (new Insets(2,2,2,2));
		pane.add(buttonJPanel, c);



		JButton clearButton = new JButton("C");
		clearButton.setActionCommand("C");
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				jMemory.setText("0");
				jHint.setText("0");
				jResult.setText("0");
			}
		});

		JButton equalsButton = new JButton("=");
		equalsButton.setActionCommand("=");
		equalsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{

				Double newElement = Double.valueOf(jTextField.getText());
				String result = null;

				String hintElement = jHint.getText();

				result = String.valueOf(hintElement + " " + getIntegerIfLikeDouble(newElement));
				jHint.setText("0");
				jResult.setText("0");

				jTextField.setText(getIntegerIfLikeDouble(Double.valueOf(computeFormula(result))));

			}

			private String getIntegerIfLikeDouble(Double dResult) {
				BigDecimal bigDecimal;
				int intValue;
				String decimalPart;
				String result;
				bigDecimal = new BigDecimal(String.valueOf(dResult));
				intValue = bigDecimal.intValue();
				decimalPart = bigDecimal.subtract(new BigDecimal(intValue)).toPlainString();


				if (decimalPart.equals("0.0")) {
					result = String.valueOf(intValue);
				} else {
					result = String.valueOf(dResult);
				}
				return result;
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.ipadx = 39;
		c.ipady = 30;
		c.gridwidth = 1;
		c.insets = (new Insets(2,2,8,0));
		pane.add(clearButton, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 2;
		c.insets = (new Insets(2,0,8,2));
		pane.add(equalsButton, c);



		jFrame.add(pane);
		jFrame.pack();
		jFrame.setVisible(true);
	}


	/**
	 * Select and Init the Look And Feel System (GUI)
	 * @param LookAndFeelName
	 * @param fallBack
	 * @throws HeadlessException
	 */
	private void initSystemLookAndFeel(String LookAndFeelName, int fallBack) throws HeadlessException {
		String[] systemLookAndFeel = {
				"com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
				"com.sun.java.swing.plaf.motif.MotifLookAndFeel",
				"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
		};
		try { 
			boolean founded = false;
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (LookAndFeelName.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					founded = true;
					break;
				}
			}
			if (!founded) {
				UIManager.setLookAndFeel(systemLookAndFeel[fallBack]);
			}
		} 
		catch (Exception exception) { 
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "ClassNotFoundException: " + e.getMessage());
			} catch (InstantiationException e) {
				JOptionPane.showMessageDialog(null, "InstantiationException: " + e.getMessage());
			} catch (IllegalAccessException e) {
				JOptionPane.showMessageDialog(null, "IllegalAccessException: " + e.getMessage());
			} catch (UnsupportedLookAndFeelException e) {
				JOptionPane.showMessageDialog(null, "UnsupportedLookAndFeelException: " + e.getMessage());
			}
		}
	}


	/**
	 * Parse a string and compute the formula inside
	 * @param input
	 * @return
	 * @throws NumberFormatException
	 */
	private static String computeFormula(String input) throws NumberFormatException {
		String[] formulas = input.split("\\s+");
		Double dres = 0.0d;

		// Lets work with prioritary operator
		boolean prioritaryDone = false;
		while(!prioritaryDone)
		{
			prioritaryDone = true;
			int count = 0;
			for (int i =0 ; i < formulas.length; i++) {
				if (isPrioritaryOperator(formulas[i]) && i>0 && i < (formulas.length -1)) {
					prioritaryDone = false;
					dres = calc(Double.valueOf(formulas[i-1]) , formulas[i], Double.valueOf(formulas[i+1]));

					formulas[i-1] = String.valueOf(dres);
					formulas[i] = "done";
					formulas[i+1] = String.valueOf(dres);
					count++;
					break;
				}
			}     

			List<String> withoutPrioritaty = new ArrayList<String>();
			for (int i = 0 ; i < formulas.length; i++) { 
				if(formulas[i] == "done") {
					i++;
					continue;
				}
				withoutPrioritaty.add(formulas[i]);
			}

			formulas = new String[formulas.length - count * 2];
			count = 0;

			for(String elem: withoutPrioritaty) {
				formulas[count++] = elem;
			}
		}
		// Lets work with secondary operator
		boolean secondaryDone = false;
		while(!secondaryDone)
		{
			secondaryDone = true;
			int count = 0;
			for (int i =0 ; i < formulas.length; i++) {
				if (isSecondaryOperator(formulas[i]) && i>0 && i < (formulas.length -1)) {
					secondaryDone = false;
					dres = calc(Double.valueOf(formulas[i-1]) , formulas[i], Double.valueOf(formulas[i+1]));

					formulas[i-1] = String.valueOf(dres);
					formulas[i] = "done";
					formulas[i+1] = String.valueOf(dres);
					count++;
					break;
				}
			}     


			List<String> withoutPrioritaty = new ArrayList<String>();
			for (int i = 0 ; i < formulas.length; i++) { 
				if(formulas[i] == "done") {
					i++;
					continue;
				}
				withoutPrioritaty.add(formulas[i]);
			}

			formulas = new String[formulas.length - count * 2];
			count = 0;

			for(String elem: withoutPrioritaty) {
				formulas[count++] = elem;
			}
		}
		return formulas[0];
	}

	/**
	 * Simple operation using to compute a biggest formula
	 * @param first
	 * @param operator
	 * @param second
	 * @return
	 */
	static double calc(double first, String operator, double second) {
		switch(operator.toString()) {
		case "x":
		case "×":
		case "*": 
			return (first * second);
		case "/":
		case "÷":
			return (first/second);
		case "+":
			return (first + second);
		case "-":
			return (first - second);
		default:
			return 0.0d;
		}
	}

	/**
	 * test if it's prioritary operation (multiplication and division)
	 * @param c
	 * @return
	 */
	public static boolean isPrioritaryOperator(String c) { 
		if (	c.equals("x")
				|| c.equals("×")
				|| c.equals("*")
				|| c.equals("/")
				|| c.equals("÷")
				|| c.equals("^") // @TODO
				) { 
			return true; 
		} 
		return false; 
	}	

	/**
	 * Test if it's a secondary operation (Addition or substraction)
	 * @param c
	 * @return
	 */
	public static boolean isSecondaryOperator(String c) { 
		if (	c.equals("+")
				|| c.equals("-")
				) { 
			return true; 
		} 
		return false; 
	}

	/**
	 * Add simple button with no special ActionListener
	 * @param parent
	 * @param label
	 */
	private void addNumberButton(Container parent, String label)
	{

		JButton button = new JButton(label);
		button.setActionCommand(label);
		button.addActionListener(this);
		parent.add(button);
	}

	/**
	 * Add action button with similar ActionListener
	 * @param parent
	 * @param label
	 */
	private void addActionButton(Container parent, String label)
	{
		JButton button = new JButton(label);
		button.setActionCommand(label);
		OperatorAction addAction = new OperatorAction();
		button.addActionListener(addAction);
		parent.add(button);
	}

	/**
	 * actionPerformed on number click
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		jTextField.setText(returnCorrectDisplayNumber(event.getActionCommand()));
	}

	/**
	 * Check for number provided
	 * 		contain ony one dot
	 * 		fix the number of zero after dot filter
	 * @param newButtonValue
	 * @return
	 */
	public String returnCorrectDisplayNumber(String newButtonValue) {

		String actualContent = jTextField.getText() + newButtonValue;
		String result = actualContent;

		try {
			if (actualContent.contains(".")) {
				String newValue = String.valueOf(Double.valueOf(actualContent));
				if (newButtonValue.equals(".")) {
					newValue = newValue.substring(0, newValue.length() - 1);
				} else if (newButtonValue.equals("0")) {
					newValue += "0";
				}
				result = newValue;

			} else {
				result = String.valueOf(Long.valueOf(actualContent));
			}
		} catch (Exception e) {
			// Nothing to do
		}
		return result;
	}

	/**
	 * Special class for action button
	 * @author PC
	 *
	 */
	private class OperatorAction implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			Double newElement = Double.valueOf(jTextField.getText());
			Double memoryElement = Double.valueOf(jMemory.getText());
			Double dResult = null;
			String result = null;

			String hintElement = jHint.getText();

			String buttonClicked = ((JButton) event.getSource()).getText();
			switch (buttonClicked) {
			case "M+":
				dResult = memoryElement + newElement;  	
				result = getIntegerIfLikeDouble(dResult);
				jMemory.setText(result);
				break;
			case "M-":
				dResult = memoryElement - newElement;
				result = getIntegerIfLikeDouble(dResult);
				jMemory.setText(result);
				break;
			case "MC":
				jMemory.setText("0");
				break;
			case "MR":
				jTextField.setText(jMemory.getText());
				break;   
			case "÷":
			case "×":
			case "-":
			case "+":
				result = printToHint(newElement, hintElement, buttonClicked);
				jHint.setText(result);
				jResult.setText(getIntegerIfLikeDouble(Double.valueOf(computeFormula(result))));
				jTextField.setText("0");
				break;
			case "±":
				dResult = 0 - newElement;  	
				result = getIntegerIfLikeDouble(dResult);
				jTextField.setText(result);
				break;
			default:
				break;
			}
		}
		/**
		 * return a formatted text to jHint  
		 * @param newElement
		 * @param hintElement
		 * @param buttonClicked
		 * @return
		 */
		private String printToHint(Double newElement, String hintElement, String buttonClicked) {
			String result;
			if ("0".equals(hintElement)) {
				result = String.valueOf(getIntegerIfLikeDouble(newElement) + " " +buttonClicked);
			} else {
				result = String.valueOf(hintElement + " " + getIntegerIfLikeDouble(newElement) + " " +buttonClicked);
			}
			return result;
		}

		/**
		 * Choose between double or integer version of a number
		 * 		15.0 	will be change to 	15
		 * 		16.2 	stay 				16.2
		 * 		17 		stay 				17
		 * @param dResult
		 * @return
		 */
		private String getIntegerIfLikeDouble(Double dResult) {
			BigDecimal bigDecimal;
			int intValue;
			String decimalPart;
			String result;
			bigDecimal = new BigDecimal(String.valueOf(dResult));
			intValue = bigDecimal.intValue();
			decimalPart = bigDecimal.subtract(new BigDecimal(intValue)).toPlainString();


			if (decimalPart.equals("0.0")) {
				result = String.valueOf(intValue);
			} else {
				result = String.valueOf(dResult);
			}
			return result;
		}

	}
}