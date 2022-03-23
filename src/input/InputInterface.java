package input;

import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

/**
 * Basic input reading.
 * @author ThinkPad
 *
 */
public interface InputInterface {
	public ConcurrentSkipListSet<DeviceInterface> readAll();
}
