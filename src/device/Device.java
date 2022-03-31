package device;

public class Device implements DeviceInterface{
	
	protected String code;
	protected String name;
	protected String owner;
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
		return code + "," + name + "," + owner + "," + inputDate + "," + String.valueOf(warrantyYear) + '\n';
	}
	
	@Override
	public int compareTo(DeviceInterface device) {
		int warrantyYearDifference = warrantyYear-device.getWarrantyYear();
		if (warrantyYearDifference != 0)
			return warrantyYearDifference;
		
		return code.compareTo(device.getCode());
	}

	/**
	 * Slightly improve run time.
	 */
	@Override
	public void standardizedOwner() {
		owner = owner.trim();

		StringBuilder temp = new StringBuilder(String.valueOf(Character.toUpperCase(owner.charAt(0))));
		for (int i=1; i < owner.length(); i++) {
			if (owner.charAt(i-1) == ' ') {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp.append(Character.toUpperCase(owner.charAt(i)));
			}
			else {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp.append(Character.toLowerCase(owner.charAt(i)));
				else {
					temp.append(' ');
				}
			}
		}
		owner = temp.toString();
	}
}
