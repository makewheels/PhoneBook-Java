package contact;

//联系人类
public class Contact {
	// 联系人姓名
	private String contactName;
	// 手机号
	private String phoneNumber;
	// 备注
	private String note;

	// 空参构造器
	public Contact() {
		super();
	}

	// 带参构造器
	public Contact(String contactName, String phoneNumber, String note) {
		super();
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
		this.note = note;
	}

	// get和set方法
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
