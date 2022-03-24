package input.parser;

import device.Device;
import device.DeviceInterface;

public class Parser implements ParserInterface{

	@Override
	public DeviceInterface parseString(String line) {
		try {
			String[] arr = line.split(",");
			int warrantyYear = Integer.valueOf(arr[4]).intValue();
			return new Device(arr[0], arr[1], arr[2], arr[3], warrantyYear);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
		
	}

}
