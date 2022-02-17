package input;

import java.util.List;

import device.DeviceInterface;

/**
 * Basic input reading.
 * @author ThinkPad
 *
 */
public interface InputInterface {
	public List<DeviceInterface> readAll();
}
