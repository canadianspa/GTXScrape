package BNQ;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class EDCmaker {

	private JFrame frame;
	private JTextField txt_purch;
	private JTextField txt_invoice;
	private JTextField txt_delDate;
	public ArrayList<EDC> allEDC; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EDCmaker window = new EDCmaker();
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
	public EDCmaker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblNewLabel = new JLabel("Purchase Order Number");
		frame.getContentPane().add(lblNewLabel);

		txt_purch = new JTextField();
		frame.getContentPane().add(txt_purch);
		txt_purch.setColumns(10);

		JLabel lblInvoiceNumber = new JLabel("Invoice Number");
		frame.getContentPane().add(lblInvoiceNumber);

		txt_invoice = new JTextField();
		txt_invoice.setColumns(10);
		frame.getContentPane().add(txt_invoice);

		JLabel lblDeliveryDate = new JLabel("Delivery Date");
		frame.getContentPane().add(lblDeliveryDate);

		txt_delDate = new JTextField();
		txt_delDate.setColumns(10);
		frame.getContentPane().add(txt_delDate);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					FileInputStream fiut = new FileInputStream("all.EDA");
					ObjectInputStream ois;
					ArrayList<EDA> alreadyGot;
					
					try {
						//cant read first time
						ois = new ObjectInputStream(fiut);
						alreadyGot = (ArrayList<EDA>) ois.readObject();
					} catch (Exception f) {
						alreadyGot = new ArrayList<EDA>();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().add(btnAdd);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);

		JButton btnCreateEDC = new JButton("Create EDC");
		frame.getContentPane().add(btnCreateEDC);
	}

}
