package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import device.DeviceInterface;
import input.parser.ParserInterface;

public class InputThread implements Runnable {

	private RandomAccessFile reader;
	private ConcurrentHashMap<String, DeviceInterface> concurrentHashMap;

	private long numberOfCharsPerThread;
	private int numberOfThread;
	private int numberOfSplits;
	private long length;
//	public static final int numberOfThreadsLimit = 4;
//	private Semaphore semaphore = new Semaphore(InputThread.numberOfThreadsLimit);

	private String line;

	public InputThread(RandomAccessFile reader, ConcurrentHashMap<String, DeviceInterface> concurrentHashMap,
			long numberOfCharsPerThread, int numberOfThread, long length) {
		this.reader = reader;
		this.concurrentHashMap = concurrentHashMap;
		this.numberOfCharsPerThread = numberOfCharsPerThread;
		this.numberOfThread = numberOfThread;
		this.length = length;
		this.numberOfSplits = 0;
	}

	@Override
	public void run() {

		try {
//			semaphore.acquire();
			for (; numberOfSplits * numberOfCharsPerThread * Input.numberOfThreads < length; numberOfSplits++) {

				if (numberOfThread > 0 || numberOfSplits > 0) {
					reader.seek(numberOfCharsPerThread * numberOfThread
							+ numberOfSplits * numberOfCharsPerThread * Input.numberOfThreads);
					while (true) {
						int c = reader.read();
						if (c == -1 || c == 0) {
							reader.close();
							return;
						}
						if (c == '\n') {
							break;
						}
					}
				}

				FileReader fr = new FileReader(reader.getFD());
				BufferedReader bufferedReader = new BufferedReader(fr);

				for (long i = 0; i < numberOfCharsPerThread;) {
					line = bufferedReader.readLine();
					if (line == null)
						break;

					i += line.length();
					DeviceInterface device = ParserInterface.parseString(line);
					concurrentHashMap.put(device.getCode(), device);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
//			semaphore.release();
		}

	}

}
