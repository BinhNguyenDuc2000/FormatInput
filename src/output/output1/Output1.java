package output.output1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import output.output1.consumer.Output1Consumer;
import output.output1.producer.Output1Producer;

/**
 * Reading from Middle Output Files and printing it to the final Output file.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class Output1 {
	private int range;
	private BufferedWriter writer;

	/**
	 * Initializing the writer and range.
	 * @param writer the writer used to print to final Output file.
	 * @param range the range of warranty year.
	 */
	public Output1(BufferedWriter writer, int range) {
		try {
			this.writer = writer;
			this.range = range;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Executing printing task 1 to file.
	 * 
	 * The process is split into 2 threads: Consumer and Producer. 
	 * - Producer will read records from Middle Output files and add it to the Blocking Queue.
	 * - Consumer will take records from Blocking Queue and print it to final Output file.
	 */
	public void printTask1() {
		try {
			BlockingQueue<String> dataQueue = new ArrayBlockingQueue<String>(1000);
			BufferedReader readerList[] = new BufferedReader[range];
			for (int i=0; i<range; i++) {
				readerList[i] = new BufferedReader(new FileReader("MiddleOutput/MiddleOutput" + i + ".txt"), 8192*4);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(11);
			executorService.execute(new Output1Producer(dataQueue, readerList));
			executorService.execute(new Output1Consumer(dataQueue, writer));
			executorService.shutdown();
			while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} 

	}

}
