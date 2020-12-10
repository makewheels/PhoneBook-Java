package frame;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

//结果TableModel
public class ResultTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -507835789112043883L;

	// 表头
	private final ArrayList<String> header = new ArrayList<String>();
	// 表中所有数据
	private ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();

	// 构造器
	public ResultTableModel(ArrayList<ArrayList<String>> allData) {
		header.add("姓名");
		header.add("号码");
		header.add("备注");

		this.allData = allData;
	}

	// 返回Table总行数
	public int getRowCount() {
		return allData.size();
	}

	// 返回Table中列数
	public int getColumnCount() {
		return header.size();
	}

	// 返回指定点的Value
	public String getValueAt(int rowIndex, int columnIndex) {
		return allData.get(rowIndex).get(columnIndex);
	}

	// 返回列的名字
	@Override
	public String getColumnName(int column) {
		return header.get(column);
	}
}
