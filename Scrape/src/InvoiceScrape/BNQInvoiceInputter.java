package InvoiceScrape;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BNQInvoiceInputter{

	private JFrame frame;
	private JTextField txtUpToPage;
	private JTextField txtUpToPlace;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BNQInvoiceInputter window = new BNQInvoiceInputter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BNQInvoiceInputter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Up To Page");
		frame.getContentPane().add(lblNewLabel);
		
		txtUpToPage = new JTextField();
		frame.getContentPane().add(txtUpToPage);
		txtUpToPage.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Up To Place");
		frame.getContentPane().add(lblNewLabel_1);
		
		txtUpToPlace = new JTextField();
		frame.getContentPane().add(txtUpToPlace);
		txtUpToPlace.setColumns(10);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] myArg = new String[2];
				myArg[0] = txtUpToPage.getText();
				myArg[1] = txtUpToPlace.getText();
				BNQInvoiceMultiScraper.launch(BNQInvoiceMultiScraper.class,myArg);
				
			}
		});
		frame.getContentPane().add(btnSubmit);
	}

	

}
