package input;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Semaphore;

import device.DeviceInterface;
import input.parser.ParserInterface;

public class InputThread implements Runnable{
	final int MAX_THREADS = 256;
	final Semaphore semaphore = new Semaphore(MAX_THREADS);
	
	private String line;
	private ConcurrentSkipListSet<DeviceInterface> skipListSet;
	public InputThread(String line, ConcurrentSkipListSet<DeviceInterface> skipListSet) {
		this.line = line;
		this.skipListSet = skipListSet;
	}
	
	@Override
	public void run() {
		try {
			semaphore.acquire();
			DeviceInterface device = ParserInterface.parseString(line);
			device.standardizedOwner();
			skipListSet.add(device);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			semaphore.release();
		}
	}
	
}
