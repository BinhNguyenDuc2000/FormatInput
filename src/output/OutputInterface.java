package output;

import java.util.List;

import device.DeviceInterface;

/**
 * Parse line by line and print output file.
 * @author ThinkPad
 *
 */
public interface OutputInterface {
	public void printTask1(List<DeviceInterface> deviceList);
	public void printTask2(List<DeviceInterface> deviceList);
	public void close();
}
