package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentSkipListSet;

import device.DeviceInterface;
import input.parser.ParserInterface;

public class InputThread implements Runnable {

	private RandomAccessFile reader;
	private ConcurrentSkipListSet<DeviceInterface> deviceListSet;
	private double numberOfCharsPerThread;
	private int numberOfThread;
	private int numberOfSplits;
//	public static final int numberOfThreadsLimit = 4;
//	private Semaphore semaphore = new Semaphore(InputThread.numberOfThreadsLimit);

	private String line;

	public InputThread(RandomAccessFile reader, ConcurrentSkipListSet<DeviceInterface> deviceListSet,
			double numberOfCharsPerThread, int numberOfThread) {
		this.reader = reader;
		this.deviceListSet = deviceListSet;
		this.numberOfCharsPerThread = numberOfCharsPerThread;
		this.numberOfThread = numberOfThread;
		this.numberOfSplits = 0;
	}

	@Override
	public void run() {

		try {
//			semaphore.acquire();
			
			for (; numberOfSplits < Input.totalNumberOfSplits/Input.totalNumberOfThreads; numberOfSplits++) {
				if (numberOfThread > 0 || numberOfSplits > 0) {
					reader.seek((long) (numberOfCharsPerThread * (double)numberOfThread
							+ (double) numberOfSplits * numberOfCharsPerThread * (double)Input.totalNumberOfThreads));

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
				BufferedReader bufferedReader = new BufferedReader(fr, 32768);

				for (Double i = 0.0; i <= numberOfCharsPerThread + Input.marginOfError;) {
					line = bufferedReader.readLine();
					if (line == null) {
						break;
					}

					i += line.length();
					DeviceInterface device = ParserInterface.parseString(line);
					deviceListSet.add(device);
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
