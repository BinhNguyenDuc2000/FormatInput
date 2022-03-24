package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import device.DeviceInterface;

public class Input implements InputInterface {
	private String filename;
	private int numberOfLinesPerThread; 
	private static final int numberOfThreads = 4;

	public Input(String filename, int numberOfLines) {
		try {
			numberOfLinesPerThread = numberOfLines/numberOfThreads;
			this.filename = filename;
			
		} catch (Exception e) {
			System.out.println(Paths.get(this.filename).toAbsolutePath());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public List<DeviceInterface> readAll() {
		try {
			ConcurrentHashMap<String, DeviceInterface> deviceList = new ConcurrentHashMap<>();
			ExecutorService es = Executors.newCachedThreadPool();

			for (int i=0; i < numberOfThreads;i++) {
				BufferedReader reader = new BufferedReader(new FileReader(this.filename), 16384);
				InputThread inputThread = new InputThread(reader, deviceList, numberOfLinesPerThread, i);
				es.execute(inputThread);
			}
			es.shutdown();
			while(!es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
			
			return new ArrayList<DeviceInterface>(deviceList.values());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}
