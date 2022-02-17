package device;

public class Device2 extends Device{

	public Device2(String code, String name, String owner, String inputDate, int warrantyYear) {
		super(code, name, owner, inputDate, warrantyYear);
	}
	
	@Override
	public void standardizedOwner() {
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
		owner = temp;
	}

}
