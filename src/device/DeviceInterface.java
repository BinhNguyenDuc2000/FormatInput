package device;

/**
 * Device entity.
 * @author ThinkPad
 */

public interface DeviceInterface {
	/**
	 * Standardized Owner name.
	 */
	public void standardizedOwner();
	
	public int compareTo(DeviceInterface a2);
	public int getWarrantyYear();
	public String getCode();
	
	public String toStandardizedString();
}
