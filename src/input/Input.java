package input;

import java.io.File;
import java.io.RandomAccessFile;
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
	private long numberOfCharsPerThread;
	public static final int numberOfThreads = 4;
	private static final int sizeOfChar = 1;
	private static final int numberOfSplits = 50;

	public Input(String filename) {
		try {
			File file = new File(filename);
			numberOfCharsPerThread = file.length() / (numberOfThreads * sizeOfChar * numberOfSplits);
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
			ExecutorService es;
			ArrayList<RandomAccessFile> rafList = new ArrayList<>();
			for (int i = 0; i < numberOfThreads; i++) {
				rafList.add(new RandomAccessFile(filename, "r"));
			}
			es = Executors.newCachedThreadPool();

			for (int i = 0; i < numberOfThreads; i++) {

				InputThread inputThread = new InputThread(rafList.get(i), deviceList, numberOfCharsPerThread, i, numberOfCharsPerThread * numberOfThreads * sizeOfChar * numberOfSplits);
				es.execute(inputThread);
			}
			es.shutdown();
			while (!es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES))
				;
			for (int i = 0; i < numberOfThreads; i++) {
				rafList.get(i).close();
			}
			return new ArrayList<DeviceInterface>(deviceList.values());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}
