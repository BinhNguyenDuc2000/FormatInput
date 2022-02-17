package output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
	public void printTask1(List<DeviceInterface> deviceList) {
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
	public void printTask2(List<DeviceInterface> deviceList) {
		try {
			ListIterator<DeviceInterface> deviceIterator = deviceList.listIterator(deviceList.size());
			while (deviceIterator.hasPrevious()) {
				DeviceInterface device = deviceIterator.previous();
				device.standardizedOwner();
				writer.write(device.toString());
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
