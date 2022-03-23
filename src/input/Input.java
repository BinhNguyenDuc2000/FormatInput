package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import device.DeviceInterface;

public class Input implements InputInterface{
	private String filename;
	protected BufferedReader reader;

	public Input(String filename) {
		try {
			this.filename = filename;
			reader = new BufferedReader(new FileReader(this.filename));
		} catch (Exception e) {
			System.out.println(Paths.get(this.filename).toAbsolutePath());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public ConcurrentSkipListSet<DeviceInterface> readAll() {
		try {
			ConcurrentSkipListSet<DeviceInterface> deviceList = new ConcurrentSkipListSet<DeviceInterface>((device1, device2)->(device1.compareTo(device2)));
			String line;
			ExecutorService es = Executors.newCachedThreadPool();

			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				InputThread inputThread = new InputThread(line, deviceList);
				es.execute(inputThread);
			}
			es.shutdown();
			while(!es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
			reader.close();
			return deviceList;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}
