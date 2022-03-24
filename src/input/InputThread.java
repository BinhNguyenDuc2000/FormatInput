package input;

import java.io.BufferedReader;
import java.util.concurrent.ConcurrentHashMap;

import device.DeviceInterface;
import input.parser.ParserInterface;

public class InputThread implements Runnable {
	
	private BufferedReader reader;
	private ConcurrentHashMap<String, DeviceInterface> concurrentHashMap;
	
	private int numberOfLinesPerThread;
	private int numberOfThread;

	private String line;
	
	public InputThread(BufferedReader reader, ConcurrentHashMap<String, DeviceInterface> concurrentHashMap, int numberOfLinesPerThread, int numberOfThread) {
		this.reader = reader;
		this.concurrentHashMap = concurrentHashMap;
		this.numberOfLinesPerThread = numberOfLinesPerThread;
		this.numberOfThread = numberOfThread;
	}
	
	@Override
	public void run() {
		
		try {
			for (int i=0; i < numberOfLinesPerThread*numberOfThread; i++) {
				reader.readLine();
			}
			for (int i=0; i < numberOfLinesPerThread; i++) {
				line = reader.readLine();
				if (line == null)
					break;
				DeviceInterface device = ParserInterface.parseString(line);
				concurrentHashMap.put(device.getCode(), device);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			
		}
		
		
	}

}
