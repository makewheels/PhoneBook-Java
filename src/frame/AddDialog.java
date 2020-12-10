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

import contact.Contact;
import database.ContactDAO;

//新增联系人窗体
public class AddDialog extends JDialog {
	private static final long serialVersionUID = 5224767995521832052L;

	// ContactDAO的引用
	private ContactDAO contactDAO;
	// mainFrame引用
	private MainFrame mainFrame;
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

	// 新增按钮
	private JButton button_add = new JButton("新增");
	// 取消按钮
	private JButton button_cancel = new JButton("取消");

	// 空参构造器
	public AddDialog(ContactDAO contactDAO, MainFrame mainFrame) {
		super(mainFrame, true);
		// 初始化ContactDAO
		this.contactDAO = contactDAO;
		// 初始化mainFrame
		this.mainFrame = mainFrame;
		// 初始化组件
		initComponents();
		// 添加监听器
		addListeers();
		// 初始化窗体
		initFrame();
	}

	// 初始化组件
	private void initComponents() {
		label_contactName.setBounds(20, 20, 50, 30);
		Label_phonenumber.setBounds(20, 70, 50, 30);
		label_note.setBounds(20, 120, 50, 30);

		textfield_contactName.setBounds(70, 20, 200, 30);
		textfield_phonenumber.setBounds(70, 70, 200, 30);
		textfield_note.setBounds(70, 120, 200, 30);

		button_add.setBounds(35, 170, 100, 30);
		button_cancel.setBounds(165, 170, 100, 30);

		this.add(label_contactName);
		this.add(Label_phonenumber);
		this.add(label_note);
		this.add(textfield_contactName);
		this.add(textfield_phonenumber);
		this.add(textfield_note);
		this.add(button_add);
		this.add(button_cancel);
	}

	// 添加监听器
	private void addListeers() {
		// 新增按钮监听
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addContact();
			}
		});

		// 取消按钮监听
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddDialog.this.dispose();
			}
		});

		// 三个TextField的"回车键监听"
		textfield_contactName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addContact();
				}
			}
		});
		textfield_phonenumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addContact();
				}
			}
		});
		textfield_note.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addContact();
				}
			}
		});
	}

	// 添加联系人方法
	private void addContact() {
		String contactName = textfield_contactName.getText().trim();
		String phonenumber = textfield_phonenumber.getText().trim();
		String note = textfield_note.getText().trim();

		if (contactName.equals("") && phonenumber.equals("")) {
			JOptionPane.showMessageDialog(AddDialog.this, "请填写姓名和号码！", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (contactName.equals("")) {
			JOptionPane.showMessageDialog(AddDialog.this, "请填写姓名！", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (phonenumber.equals("")) {
			JOptionPane.showMessageDialog(AddDialog.this, "请填写号码！", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 判断该联系人是否已存在，如果已经存在，提示并返回
		if (contactDAO.isContactExist(contactName, phonenumber, note) == true) {
			JOptionPane.showMessageDialog(AddDialog.this, "该联系人已存在，不能重复添加！",
					"错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 能到这里说明没问题了，那就添加联系人到数据库
		contactDAO.addContact(new Contact(contactName, phonenumber, note));
		AddDialog.this.dispose();
	}

	// 初始化窗体
	private void initFrame() {
		this.setTitle("新增联系人");
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
}
