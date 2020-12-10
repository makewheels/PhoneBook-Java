package frame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.ContactDAO;

//查询Dialog
public class QueryDialog extends JDialog {
	private static final long serialVersionUID = 8881470257513615224L;

	// 引用contactDAO
	ContactDAO contactDAO;
	// 引用mainFrame
	MainFrame mainFrame;

	// 查询条件Label
	private JLabel label_queryCondition = new JLabel("查询条件:");
	// 姓名RadioButton
	private JRadioButton radiobutton_name = new JRadioButton("姓名");
	// 电话RadioButton
	private JRadioButton radiobutton_number = new JRadioButton("电话");
	// 备注RadioButton
	private JRadioButton radiobutton_note = new JRadioButton("备注");
	// 查询内容Label
	private JLabel label_queryContent = new JLabel("查询内容:");
	// 单选按钮组
	private ButtonGroup buttongroup_queryCondition = new ButtonGroup();
	// 查询内容TextField
	private JTextField textfield_queryContent = new JTextField();
	// 按条件查询按钮
	private JButton button_queryByCondition = new JButton("按指定条件查询");
	// 查看所有联系人按钮
	private JButton button_queryAll = new JButton("查看所有联系人");
	// 取消按钮
	private JButton button_cancel = new JButton("取消");

	// 带参构造器
	public QueryDialog(ContactDAO contactDAO, MainFrame mainFrame) {
		super(mainFrame, true);
		// /初始化contactDAO
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
		// 默认查询内容TextField获得焦点

		// 设置查询条件的单选按钮组
		buttongroup_queryCondition.add(radiobutton_name);
		buttongroup_queryCondition.add(radiobutton_number);
		buttongroup_queryCondition.add(radiobutton_note);

		// 单选按钮组默认选中姓名
		radiobutton_name.setSelected(true);

		label_queryCondition.setBounds(50, 10, 60, 30);
		radiobutton_name.setBounds(30, 40, 55, 30);
		radiobutton_number.setBounds(85, 40, 55, 30);
		radiobutton_note.setBounds(140, 40, 55, 30);
		label_queryContent.setBounds(50, 70, 55, 30);
		textfield_queryContent.setBounds(30, 110, 165, 30);
		button_queryByCondition.setBounds(45, 160, 130, 30);
		button_queryAll.setBounds(45, 200, 130, 30);
		button_cancel.setBounds(45, 240, 130, 30);

		this.add(label_queryCondition);
		this.add(radiobutton_name);
		this.add(radiobutton_number);
		this.add(radiobutton_note);
		this.add(label_queryContent);
		this.add(textfield_queryContent);
		this.add(button_queryByCondition);
		this.add(button_queryAll);
		this.add(button_cancel);

	}

	// 添加监听器
	private void addListeers() {
		// 按指定条件查询
		button_queryByCondition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 拿到查询内容
				String queryContent = textfield_queryContent.getText().trim();
				// 如果查询内容为空，通知并返回
				if (queryContent.equals("")) {
					JOptionPane.showMessageDialog(QueryDialog.this,
							"请先输入查询内容!", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 再看看选中了哪个单选按钮
				int queryConditionInt = 0;
				if (radiobutton_name.isSelected() == true) {
					queryConditionInt = 1;
				} else if (radiobutton_number.isSelected() == true) {
					queryConditionInt = 2;
				} else if (radiobutton_note.isSelected() == true) {
					queryConditionInt = 3;
				}
				// 从数据库查询的返回结果
				ArrayList<ArrayList<String>> resultData = null;
				// 交给数据库查询
				if (queryConditionInt != 0) {
					resultData = contactDAO.queryContact(queryConditionInt,
							queryContent);
				}
				// 如果查询结果为空
				if (resultData.size() == 0) {
					// 确定查询条件的中文
					String queryConditionString = "";
					if (queryConditionInt == 1) {
						queryConditionString = "姓名";
					} else if (queryConditionInt == 2) {
						queryConditionString = "电话";
					} else if (queryConditionInt == 3) {
						queryConditionString = "备注";
					}
					// 通知
					JOptionPane
							.showMessageDialog(mainFrame,
									"很抱歉，未查到您指定的条件的联系人！您可以检查条件:\n"
											+ queryConditionString + ":"
											+ queryContent, "通知",
									JOptionPane.ERROR_MESSAGE);
					// 如果查询结果不为空
				} else {
					// 把查询结果更新到Table中
					mainFrame.setTable(resultData);
					// 通知结果
					JOptionPane.showMessageDialog(mainFrame,
							"共查到" + resultData.size() + "个联系人！", "通知",
							JOptionPane.INFORMATION_MESSAGE);
					// 销毁本窗体
					QueryDialog.this.dispose();
				}
			}
		});

		// 查看所有联系人按钮监听
		button_queryAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 使用主窗体中的刷新Table方法
				mainFrame.refreshTable();
				// 销毁本窗体
				QueryDialog.this.dispose();
			}
		});

		// 取消按钮监听
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 销毁本窗体
				QueryDialog.this.dispose();
			}
		});
	}

	// 初始化窗体
	private void initFrame() {
		this.setTitle("查询联系人");
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
		int thisSizeX = 230;
		int thisSizeY = 320;
		// 设置相对位置
		this.setBounds(parentLocationX + parentSizeX / 2 - thisSizeX / 2,
				parentLocationY + parentSizeY / 2 - thisSizeY / 2, thisSizeX,
				thisSizeY);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
