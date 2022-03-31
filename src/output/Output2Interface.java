package output;

import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

public interface Output2Interface {
	public void printTask2(ConcurrentSkipListSet<DeviceInterface> deviceList);
}
