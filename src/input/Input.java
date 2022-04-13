package input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import controller.FormatInputController;
import input.consumer.InputConsumer;
import input.producer.InputProducer;

/**
 * Reading input file and splitting it to multiple files
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */

public class Input {
	private ArrayList<BufferedReader> readerList = new ArrayList<>();
	private int range;
	/**
	 * The number of chars each input producer thread reads.
	 */
	public static final long NUMBER_OF_CHARS_PER_THREAD = 10000000l;
	/**
	 * The size of a char
	 */
	private static final int SIZE_OF_CHAR = 1;
	
	/**
	 * Initializing the Readers list.
	 * @param filename the input file name.
	 * @param range the range of warranty year.
	 */
	public Input(String filename, int range) {
		try {
			File file = new File(filename);
			long totalNumberOfChars = file.length() / SIZE_OF_CHAR;
			long totalNumberOfReadChars = 0l;

			for (int i = 0; totalNumberOfReadChars < totalNumberOfChars; i++) {
				RandomAccessFile raf = new RandomAccessFile(new File(filename), "r");
				if (i != 0) {
					raf.seek(totalNumberOfReadChars-1);
					if (raf.read() != '\n') {
						boolean running = true;
						while (running) {
							int c = raf.read();
							totalNumberOfReadChars++;
							if (c == '\n' || c == -1 || c == 0) {
								running = false;
							}
						}
					}
					
				}

				if (totalNumberOfReadChars >= totalNumberOfChars) {
					break;
				}

				readerList.add(new BufferedReader(new FileReader(raf.getFD())));
				totalNumberOfReadChars += NUMBER_OF_CHARS_PER_THREAD;
			}
			this.range = range;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Start to read file and splitting it to multiple smaller files based on warranty year.
	 *
	 * The reading processes is split into multiple producer threads, each reading a certain amount bytes decided by NUMBER_OF_CHARS_PER_THREAD constant.
	 * Read data is then used by consumer threads through multiple blocking queue and written to middle output files.
	 */
	public void readAll() {
		try {
			// Setting up consumers
			ArrayList<BlockingQueue<String>> dataQueueList = new ArrayList<>();
			BufferedWriter writerList[] = new BufferedWriter[range];
			ExecutorService consumerExecutorService = Executors.newFixedThreadPool(10);
			for (int i = 0; i < range; i++) {
				dataQueueList.add(new ArrayBlockingQueue<String>(100000));
				writerList[i] = new BufferedWriter(new FileWriter("MiddleOutput/MiddleOutput" + i + ".txt"), 8192 * 10);
				consumerExecutorService.execute(new InputConsumer(dataQueueList.get(i), writerList[i]));
			}
			consumerExecutorService.shutdown();

			// Setting up producers
			ExecutorService producerExecutorService = Executors.newFixedThreadPool(4);

			for (int i = 0; i < readerList.size(); i++) {
				producerExecutorService.execute(new InputProducer(dataQueueList, readerList.get(i)));
			}
			producerExecutorService.shutdown();
			while (!producerExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));

			for (int index = 0; index < dataQueueList.size(); index++) {
				dataQueueList.get(index).put("end");
			}

			while (!consumerExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES))
				;
			FormatInputController.log.info("Input1 consumers finished");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
