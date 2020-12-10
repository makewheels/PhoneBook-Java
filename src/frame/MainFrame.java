package frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import database.ContactDAO;

//主界面窗体
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 8130604686798190445L;

	// 创建ContactDAO对象
	private ContactDAO contactDAO = new ContactDAO(this);
	// 显示结果的Table
	private JTable table_result = new JTable();
	// Table的滚动条
	private JScrollPane scrollpane_result = new JScrollPane(table_result);
	// 新增按钮
	private JButton button_add = new JButton("新增");
	// 删除按钮
	private JButton button_delete = new JButton("删除");
	// 修改按钮
	private JButton button_update = new JButton("修改");
	// 查询按钮
	private JButton button_query = new JButton("查询");

	// 空参构造器
	public MainFrame() {
		// 初始化ContactDAO
		contactDAO = new ContactDAO(this);
		// 初始化组件
		initComponents();
		// 添加监听器
		addListeers();
		// 初始化窗体
		initFrame();
	}

	// 初始化组件
	private void initComponents() {
		// 设置table内容居中
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(JLabel.CENTER);
		table_result.setDefaultRenderer(Object.class, dtcr);

		scrollpane_result.setBounds(30, 30, 600, 400);
		button_add.setBounds(50, 450, 100, 30);
		button_delete.setBounds(200, 450, 100, 30);
		button_update.setBounds(350, 450, 100, 30);
		button_query.setBounds(500, 450, 100, 30);

		this.add(scrollpane_result);
		this.add(button_add);
		this.add(button_delete);
		this.add(button_update);
		this.add(button_query);

		// 刚打开主界面时，就自动刷新Table
		refreshTable();
	}

	// 添加监听器
	private void addListeers() {
		// 添加按钮监听
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addContact();
			}
		});

		// 删除按钮监听
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		// 修改按钮监听
		button_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});

		// 查询按钮监听
		button_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryContact();
			}
		});
	}

	// 初始化窗体
	private void initFrame() {
		// 设置Title
		this.setTitle("电话本主窗体");
		// 设置登录Frame为null布局
		this.setLayout(null);
		// 拿到屏幕分辨率
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		// 窗体的宽度和高度
		int frameWidth = 665;
		int frameHeight = 535;
		// 显示在屏幕中央
		this.setBounds(screenWidth / 2 - frameWidth / 2, screenHeight / 2 - frameHeight / 2, frameWidth, frameHeight);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// 根据传入的ArrayList<ArrayList<String>>设置Table中的内容
	public void setTable(ArrayList<ArrayList<String>> setData) {
		table_result.setModel(new ResultTableModel(setData));
	}

	// 从数据库中读取全部数据，刷新Table中的内容
	public void refreshTable() {
		table_result.setModel(new ResultTableModel(contactDAO.getAllContact()));
	}

	// 添加联系人方法
	private void addContact() {
		// 打开添加联系人窗体，剩下的交给这窗体了
		new AddDialog(contactDAO, MainFrame.this);
		// 刷新Table
		refreshTable();
	}

	// 删除联系人方法
	private void delete() {
		// 先看看都选中了哪些行
		int[] selectedRows = table_result.getSelectedRows();
		// 如果没选，提示并返回
		if (selectedRows.length == 0) {
			JOptionPane.showMessageDialog(MainFrame.this, "请先选择要删除的联系人!\n(可选多个，支持批量删除)", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 要删除联系人的号码
		String[] deleteNumber = new String[selectedRows.length];
		// 遍历所选行，确定这些号码
		for (int i = 0; i < selectedRows.length; i++) {
			deleteNumber[i] = (String) table_result.getValueAt(selectedRows[i], 1);
		}
		// 执行删除
		contactDAO.deleteContactByPhoneNumber(deleteNumber);
		// 刷新Table
		refreshTable();
	}

	// 修改联系人方法
	private void updateContact() {
		// 先看看选中的是哪行
		int[] selectedRows = table_result.getSelectedRows();
		// 如果没选中，提示并返回
		if (selectedRows.length == 0) {
			JOptionPane.showMessageDialog(MainFrame.this, "请先选择要修改的联系人!", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 如果选中多个联系人，提示并返回
		if (selectedRows.length >= 2) {
			JOptionPane.showMessageDialog(MainFrame.this, "每次只能修改一个联系人!", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 要修改的联系人
		String[] updateContact = new String[3];
		updateContact[0] = (String) table_result.getValueAt(selectedRows[0], 0);
		updateContact[1] = (String) table_result.getValueAt(selectedRows[0], 1);
		updateContact[2] = (String) table_result.getValueAt(selectedRows[0], 2);
		// 打开更新联系人窗体，剩下的交给更新窗体了
		new UpdateDialog(MainFrame.this, contactDAO, updateContact);
		// 刷新Table
		refreshTable();
	}

	// 查询联系人方法
	private void queryContact() {
		// 直接打开查询窗体
		new QueryDialog(this.contactDAO, MainFrame.this);
	}

	// 主方法
	public static void main(String[] args) {
		new MainFrame();
	}
}
