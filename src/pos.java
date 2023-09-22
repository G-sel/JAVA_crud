import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.BoxLayout;
//import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class pos {

	private JFrame frame;
	private JTextField nameTxt;
	private JTextField editionTxt;
	private JTextField priceTxt;
	private JTextField idTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pos window = new pos();
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
	public pos() {
		initialize();
		Connect();
		table_load();
	}
	
	/**
	 * Create database connection.
	 */
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/pos_db","root","");
		}
		
		catch(ClassNotFoundException ex){}
		
		catch(SQLException ex) {}
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from product");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 628, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOKSHOP Online Shop");
		lblNewLabel.setForeground(new Color(0, 0, 160));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 612, 29);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Book Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 160)));
		panel.setBounds(10, 49, 345, 167);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 32, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Edition:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(10, 50, 46, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Price:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_3.setBounds(10, 68, 46, 14);
		panel.add(lblNewLabel_1_3);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(66, 32, 225, 16);
		panel.add(nameTxt);
		nameTxt.setColumns(10);
		
		editionTxt = new JTextField();
		editionTxt.setColumns(10);
		editionTxt.setBounds(66, 50, 225, 16);
		panel.add(editionTxt);
		
		priceTxt = new JTextField();
		priceTxt.setColumns(10);
		priceTxt.setBounds(66, 68, 225, 16);
		panel.add(priceTxt);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setForeground(new Color(0, 0, 102));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookName, edition, price;
				
				bookName = nameTxt.getText();
				edition = editionTxt.getText();
				price = priceTxt.getText();
				
				try {
				 pst = con.prepareStatement("insert into product(name, edition, price) values(?,?,?)");
				 pst.setString(1, bookName);
				 pst.setString(2, edition);
				 pst.setString(3, price);
				 pst.executeUpdate();
				 JOptionPane.showMessageDialog(null, "Record Added!");
				 table_load();
				 nameTxt.setText("");
				 editionTxt.setText("");
				 priceTxt.setText("");
				 
				 nameTxt.requestFocus();
				}
				catch(SQLException el) {
					el.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(31, 106, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(new Color(0, 0, 102));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				windowClose();
				
			}
			
			public void windowClose() {
				frame.dispose();
			}
			
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.setBackground(new Color(255, 255, 255));
		btnExit.setBounds(130, 106, 89, 23);
		panel.add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(new Color(0, 0, 102));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String bookName, edition, price, bookId;
				
//				bookName = nameTxt.getText();
//				edition = editionTxt.getText();
//				price = priceTxt.getText();
//				bookId = idTxt.getText();
//				
//				
				 nameTxt.setText("");
				 editionTxt.setText("");
				 priceTxt.setText("");
				 idTxt.setText("");
				 table_load();
				 nameTxt.requestFocus();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBackground(new Color(255, 255, 255));
		btnClear.setBounds(229, 106, 89, 23);
		panel.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(365, 49, 237, 167);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 160)));
		panel_2.setBounds(10, 230, 592, 50);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Book ID:");
		lblNewLabel_1_2_1.setBounds(10, 21, 65, 15);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(lblNewLabel_1_2_1);
		
		idTxt = new JTextField();
		idTxt.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = idTxt.getText();
					
					pst = con.prepareStatement("select name, edition, price from product where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						nameTxt.setText(name);
						editionTxt.setText(edition);
						priceTxt.setText(price);	
					}
					else {
						nameTxt.setText("");
						editionTxt.setText("");
						priceTxt.setText("");	
					}
					
				}
				catch(SQLException el) {
					
				}
				
				
				
				
				
				
			}
		});
		idTxt.setBounds(66, 19, 225, 16);
		idTxt.setColumns(10);
		panel_2.add(idTxt);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(new Color(0, 0, 102));
		btnDelete.setBounds(475, 11, 88, 29);
		panel_2.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookId;
				
				bookId = idTxt.getText();
				
				try {
				 pst = con.prepareStatement("delete from product where ID = ?");
				 pst.setString(1, bookId);
				 pst.executeUpdate();
				 JOptionPane.showMessageDialog(null, "row deleted!");
				 table_load();
				 nameTxt.setText("");
				 editionTxt.setText("");
				 priceTxt.setText("");
				 
				 nameTxt.requestFocus();
				}
				catch(SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(0, 0, 102));
		btnUpdate.setBounds(377, 11, 88, 29);
		panel_2.add(btnUpdate);
		btnUpdate.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				
				
				
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
String bookName, edition, price, bookId;
				
				bookName = nameTxt.getText();
				edition = editionTxt.getText();
				price = priceTxt.getText();
				bookId = idTxt.getText();
				
				try {
				 pst = con.prepareStatement("update product set name =?, edition = ?, price =? where ID = ?");
				 pst.setString(1, bookName);
				 pst.setString(2, edition);
				 pst.setString(3, price);
				 pst.setString(4, bookId);
				 pst.executeUpdate();
				 JOptionPane.showMessageDialog(null, "Record Updated!");
				 table_load();
				 nameTxt.setText("");
				 editionTxt.setText("");
				 priceTxt.setText("");
				 
				 nameTxt.requestFocus();
				}
				catch(SQLException el) {
					el.printStackTrace();
				}
					
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBackground(UIManager.getColor("Button.disabledShadow"));
	}
}
