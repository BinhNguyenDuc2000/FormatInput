package output;

import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

/**
 * Parse line by line and print output file.
 * @author ThinkPad
 *
 */
public interface Output1Interface {
	public void printTask1(ConcurrentSkipListSet<DeviceInterface> deviceList);
}
