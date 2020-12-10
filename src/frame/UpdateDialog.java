package frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.ContactDAO;

//修改联系人窗体
public class UpdateDialog extends JDialog {
	private static final long serialVersionUID = 468787189742551105L;

	// 引用mainFrame
	private MainFrame mainFrame;
	// 引用contactDAO
	private ContactDAO contactDAO;
	// 要修改的联系人，是改之前的
	private String[] updateContact;

	// 姓名Label
	private JLabel label_contactName = new JLabel("姓名:");
	// 号码Label
	private JLabel Label_phonenumber = new JLabel("号码:");
	// 备注Label
	private JLabel label_note = new JLabel("备注:");

	// 姓名TextField
	private JTextField textfield_contactName = new JTextField();
	// 号码TextField
	private JTextField textfield_phonenumber = new JTextField();
	// 备注TextField
	private JTextField textfield_note = new JTextField();

	// 修改按钮
	private JButton button_update = new JButton("修改");
	// 取消按钮
	private JButton button_cancel = new JButton("取消");

	// 带参构造器
	public UpdateDialog(MainFrame mainFrame, ContactDAO contactDAO,
			String[] updateContact) {
		super(mainFrame, true);
		this.mainFrame = mainFrame;
		this.contactDAO = contactDAO;
		this.updateContact = updateContact;
		// 初始化组件
		initComponents();
		// 添加监听器
		addListeers();
		// 初始化窗体
		initFrame();
	}

	// 初始化组件
	private void initComponents() {
		// 预先在TextField上填好要修改联系人的信息
		textfield_contactName.setText(updateContact[0]);
		textfield_phonenumber.setText(updateContact[1]);
		textfield_note.setText(updateContact[2]);
		label_contactName.setBounds(20, 20, 50, 30);
		Label_phonenumber.setBounds(20, 70, 50, 30);
		label_note.setBounds(20, 120, 50, 30);

		textfield_contactName.setBounds(70, 20, 200, 30);
		textfield_phonenumber.setBounds(70, 70, 200, 30);
		textfield_note.setBounds(70, 120, 200, 30);

		button_update.setBounds(35, 170, 100, 30);
		button_cancel.setBounds(165, 170, 100, 30);

		this.add(label_contactName);
		this.add(Label_phonenumber);
		this.add(label_note);
		this.add(textfield_contactName);
		this.add(textfield_phonenumber);
		this.add(textfield_note);
		this.add(button_update);
		this.add(button_cancel);
	}

	// 添加监听器
	private void addListeers() {
		// 修改按钮监听
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});

		// 取消按钮监听
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateDialog.this.dispose();
			}
		});

		// 三个TextField的"回车键监听"
		textfield_contactName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateContact();
				}
			}
		});
		textfield_phonenumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateContact();
				}
			}
		});
		textfield_note.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateContact();
				}
			}

		});
	}

	// 初始化窗体
	private void initFrame() {
		this.setTitle("修改联系人");
		this.setLayout(null);
		// 拿到父级窗口的位置
		Point point = mainFrame.getLocation();
		int parentLocationX = (int) point.getX();
		int parentLocationY = (int) point.getY();
		// 拿到父级窗口的大小
		Dimension dimension = mainFrame.getSize();
		int parentSizeX = (int) dimension.getWidth();
		int parentSizeY = (int) dimension.getHeight();
		// 本窗体的大小
		int thisSizeX = 300;
		int thisSizeY = 250;
		// 设置相对位置
		this.setBounds(parentLocationX + parentSizeX / 2 - thisSizeX / 2,
				parentLocationY + parentSizeY / 2 - thisSizeY / 2, thisSizeX,
				thisSizeY);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// 更新联系人方法
	private void updateContact() {
		// 要修改之后的联系人的信息
		String[] newContact = new String[3];
		newContact[0] = textfield_contactName.getText().trim();
		newContact[1] = textfield_phonenumber.getText().trim();
		newContact[2] = textfield_note.getText().trim();
		// 如果没有改动，返提示并返回
		if (newContact[0].equals(updateContact[0])
				&& newContact[1].equals(updateContact[1])
				&& newContact[2].equals(updateContact[2])) {
			JOptionPane.showMessageDialog(UpdateDialog.this, "未发现改动!", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 执行更新:根据电话号码改，需要之前的电话号码和改之后的全部最新信息
		contactDAO.updateContact(updateContact[1], newContact);
		// 销毁本Dialog
		UpdateDialog.this.dispose();
	}
}
