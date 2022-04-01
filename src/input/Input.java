package input;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import device.DeviceInterface;

public class Input implements InputInterface {
	private String filename;
	private double numberOfCharsPerThread;
	public static final int totalNumberOfThreads = 4;
	public static final long marginOfError = 200;
	private static final int sizeOfChar = 1;
	public static final int totalNumberOfSplits = 80;

	public Input(String filename) {
		try {
			File file = new File(filename);
			numberOfCharsPerThread = (double) file.length() / (double) (sizeOfChar * totalNumberOfSplits);
			this.filename = filename;

		} catch (Exception e) {
			System.out.println(Paths.get(this.filename).toAbsolutePath());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public ConcurrentSkipListSet<DeviceInterface> readAll() {
		try {
			ConcurrentSkipListSet<DeviceInterface> deviceList = new ConcurrentSkipListSet<>((device1, device2) -> device1.compareTo(device2));
			ExecutorService es;
			ArrayList<RandomAccessFile> rafList = new ArrayList<>();
			for (int i = 0; i < totalNumberOfThreads; i++) {
				rafList.add(new RandomAccessFile(filename, "r"));
			}
			es = Executors.newCachedThreadPool();

			for (int i = 0; i < totalNumberOfThreads; i++) {

				InputThread inputThread = new InputThread(rafList.get(i), deviceList, numberOfCharsPerThread, i);
				es.execute(inputThread);
			}
			es.shutdown();
			while (!es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES))
				;
			for (int i = 0; i < totalNumberOfThreads; i++) {
				rafList.get(i).close();
			}
			return deviceList;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}
