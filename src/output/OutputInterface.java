package output;

import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

/**
 * Parse line by line and print output file.
 * @author ThinkPad
 *
 */
public interface OutputInterface {
	public void printTask1(ConcurrentSkipListSet<DeviceInterface> deviceList);
	public void printTask2(ConcurrentSkipListSet<DeviceInterface> deviceList);
	public void close();
}
