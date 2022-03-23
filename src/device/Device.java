package device;

public class Device implements DeviceInterface{
	
	protected String code;
	protected String name;
	protected String owner;
	protected String standardizedOwner;
	protected String inputDate;
	protected int warrantyYear;
	
	public Device(String code, String name, String owner, String inputDate, int warrantyYear) {
		this.code = code;
		this.name = name;
		this.owner = owner;
		this.inputDate = inputDate;
		this.warrantyYear = warrantyYear;
	}

	@Override
	public int getWarrantyYear() {
		return warrantyYear;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return code + "," + name + "," + owner + "," + inputDate + "," + String.valueOf(warrantyYear);
	}
	
	@Override
	public String toStandardizedString() {
		return code + "," + name + "," + standardizedOwner + "," + inputDate + "," + String.valueOf(warrantyYear);
	}

	@Override
	public int compareTo(DeviceInterface device) {
		if ((warrantyYear-device.getWarrantyYear())!=0)
			return warrantyYear-device.getWarrantyYear();
		return code.compareTo(device.getCode());
	}

	@Override
	public void standardizedOwner() {
		String prestandardizedOwner = new String(owner);
		owner = owner.trim();

		String temp = String.valueOf(Character.toUpperCase(owner.charAt(0)));
		for (int i=1; i < owner.length(); i++) {
			if (owner.charAt(i-1) == ' ') {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp += Character.toUpperCase(owner.charAt(i));
			}
			else {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp += Character.toLowerCase(owner.charAt(i));
				else {
					temp += ' ';
				}
			}
		}
		owner = prestandardizedOwner;
		standardizedOwner = temp;
	}
}
