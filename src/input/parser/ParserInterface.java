package input.parser;

import device.Device;
import device.DeviceInterface;

/**
 * Read current line and return Device object.
 * 
 * @author ThinkPad
 *
 */
public interface ParserInterface {
	/**
	 * Read the line.
	 * 
	 * @return Device object
	 */
	public static DeviceInterface parseString(String line) {
		try {
			String[] arr = line.split(",");
			int warrantyYear = Integer.valueOf(arr[4]).intValue();
			return new Device(arr[0], arr[1], arr[2], arr[3], warrantyYear);
		} catch (

		Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}
}
