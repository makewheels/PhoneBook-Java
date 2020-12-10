package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import contact.Contact;
import frame.MainFrame;

//操作数据库类
public class ContactDAO {
	private Statement statement;

	// 带参构造器
	public ContactDAO(MainFrame mainFrame) {
		// 初始化statement
		this.statement = DataBase.getStatement();
	}

	// 根据resultSet返回记录
	public ArrayList<ArrayList<String>> getRecordByResultSet(ResultSet resultSet) {
		// 要返回的记录
		ArrayList<ArrayList<String>> resultData = new ArrayList<ArrayList<String>>();
		try {
			while (resultSet.next()) {
				// 每一行的记录，用于放到所有记录中
				ArrayList<String> row = new ArrayList<String>();
				// 先准备好每行
				row.add(resultSet.getString("CONTACTNAME"));
				row.add(resultSet.getString("PHONENUMBER"));
				row.add(resultSet.getString("NOTE"));
				// 把每行都加到总记录里
				resultData.add(row);
			}
			// 返回记录
			return resultData;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 返回数据库中所有联系人
	public ArrayList<ArrayList<String>> getAllContact() {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM CONTACT;");
			return getRecordByResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 判断数据库中是否已经存在该联系人
	public boolean isContactExist(String contactName, String phonenumber,
			String note) {
		return false;
	}

	// 新增联系人方法
	public void addContact(Contact contact) {
		try {
			statement
					.executeUpdate("INSERT INTO CONTACT(CONTACTNAME,PHONENUMBER,NOTE)VALUES('"
							+ contact.getContactName()
							+ "','"
							+ contact.getPhoneNumber()
							+ "','"
							+ contact.getNote() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 删除联系人方法
	public void deleteContactByPhoneNumber(String[] deleteNumber) {
		try {
			for (int i = 0; i < deleteNumber.length; i++) {
				statement
						.executeUpdate("DELETE FROM CONTACT WHERE PHONENUMBER='"
								+ deleteNumber[i] + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 修改联系人方法
	public void updateContact(String updateNumber, String[] newContact) {
		try {
			statement.executeUpdate("UPDATE CONTACT SET CONTACTNAME='"
					+ newContact[0] + "',PHONENUMBER='" + newContact[1]
					+ "',NOTE='" + newContact[2] + "'WHERE PHONENUMBER='"
					+ updateNumber + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 查询联系人方法
	public ArrayList<ArrayList<String>> queryContact(int queryCondition,
			String queryContent) {
		try {
			// 根据查询条件在数据库中查询
			ResultSet resultSet = null;
			if (queryCondition == 1) {
				resultSet = statement
						.executeQuery("SELECT * FROM CONTACT WHERE CONTACTNAME LIKE '%"
								+ queryContent + "%';");
			} else if (queryCondition == 2) {
				resultSet = statement
						.executeQuery("SELECT * FROM CONTACT WHERE PHONENUMBER LIKE '%"
								+ queryContent + "%';");
			} else if (queryCondition == 3) {
				resultSet = statement
						.executeQuery("SELECT * FROM CONTACT WHERE NOTE LIKE '%"
								+ queryContent + "%';");
			}
			return getRecordByResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
