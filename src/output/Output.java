package output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;

public class Output implements OutputInterface {

	private BufferedWriter writer;

	public Output(String filename) {
		try {
			writer = new BufferedWriter(new FileWriter(filename), 16384);
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
				writer.newLine();
			}
			writer.write("###");
			writer.newLine();
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
				writer.write(device.toStandardizedString());
				writer.newLine();
			}
			writer.write("###");
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	

}
