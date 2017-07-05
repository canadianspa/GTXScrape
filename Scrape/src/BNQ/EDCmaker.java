package BNQ;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EDCmaker {

	private JFrame frame;
	private JTextField txt_purch;
	private JTextField txt_invoice;
	private JTextField txt_delDate;
	public ArrayList<EDC> allEDC = new ArrayList<EDC>(); 
	private JTextField txt_seqNo;

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
		frame.getContentPane().setLayout(new GridLayout(6, 2, 0, 0));

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

				boolean someThingWrong = false;
				Pattern testPattern= Pattern.compile("^[0-9]{8}");
				Matcher teststring= testPattern.matcher(txt_purch.getText());

				if(!teststring.matches())
				{
					JOptionPane.showMessageDialog(null,"Purchase Order should be 8 Numbers", "EDC", JOptionPane.PLAIN_MESSAGE);
					someThingWrong = true;

				}
				testPattern= Pattern.compile("^[0-9]{5}");
				teststring= testPattern.matcher(txt_invoice.getText());

				if(!teststring.matches())
				{
					JOptionPane.showMessageDialog(null,"Invoice should be 5 Numbers", "EDC", JOptionPane.PLAIN_MESSAGE);
					someThingWrong = true;
				}

				SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
				try {
					form.parse(txt_delDate.getText());
				} catch (ParseException e2) {
					JOptionPane.showMessageDialog(null,"Date incorrect format should be dd/mm/yyyy", "EDC", JOptionPane.PLAIN_MESSAGE);
					someThingWrong = true;
				}

				if(!someThingWrong)
				{
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

						for(EDA a: alreadyGot)
						{
							System.out.println(a.purchOrderNo);
							if(a.purchOrderNo.equals(txt_purch.getText()))
							{
								JOptionPane.showMessageDialog(null,"Purchase Order Found", "EDC", JOptionPane.PLAIN_MESSAGE);
								allEDC.add(new EDC(a,txt_delDate.getText(),txt_invoice.getText()));
								break;
							}
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		frame.getContentPane().add(btnAdd);

		JLabel lblSequenceNo = new JLabel("Sequence No");
		frame.getContentPane().add(lblSequenceNo);

		txt_seqNo = new JTextField();
		txt_seqNo.setColumns(10);
		frame.getContentPane().add(txt_seqNo);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);

		JButton btnCreateEDC = new JButton("Create EDC");
		btnCreateEDC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EDC.createEDCXLS(allEDC,txt_seqNo.getText());
				JOptionPane.showMessageDialog(null,"EDC Created", "EDC", JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		frame.getContentPane().add(btnCreateEDC);
	}

}
