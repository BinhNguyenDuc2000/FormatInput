package output;

import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

public class Output1 implements Output1Interface {

	private BufferedWriter writer;

	public Output1(BufferedWriter writer) {
		try {
			this.writer = writer;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void printTask1(ConcurrentSkipListSet<DeviceInterface> deviceList) {
		try {
			Iterator<DeviceInterface> deviceIterator = deviceList.iterator();
			while (deviceIterator.hasNext()) {
				writer.write(deviceIterator.next().toString());
			}
			writer.write("###\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} 

	}

}
