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
	public String toString() {
		return code + "," + name + "," + owner + "," + inputDate + "," + String.valueOf(warrantyYear);
	}

	@Override
	public int compareTo(DeviceInterface device) {
		return warrantyYear-device.getWarrantyYear();
	}

	@Override
	public void standardizedOwner() {
		owner = owner.trim();
		owner = owner.replaceAll("\\s+", " ");
		String temp[] = owner.split(" ");
        owner = ""; 
        for (int i = 0; i < temp.length; i++) {
            owner += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1).toLowerCase();
            if (i < temp.length - 1)
                owner += " ";
        }

	}
}
