package input.parser;

import device.DeviceInterface;

/**
 * Read current line and return Device object.
 * @author ThinkPad
 *
 */
public interface ParserInterface {
	/**
	 * Read the line.
	 * 
	 * @return Device object
	 */
	public DeviceInterface parseString(String line);
	
}
