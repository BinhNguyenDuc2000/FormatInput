package output;

import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

public class Output2 implements Output2Interface {
	private BufferedWriter writer;

	public Output2(BufferedWriter writer) {
		try {
			this.writer = writer;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void printTask2(ConcurrentSkipListSet<DeviceInterface> deviceList) {
		try {
			Iterator<DeviceInterface> deviceIterator = deviceList.descendingIterator();
			while (deviceIterator.hasNext()) {
				DeviceInterface device = deviceIterator.next();
				device.standardizedOwner();
				writer.write(device.toString());
			}
			writer.write("###\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
