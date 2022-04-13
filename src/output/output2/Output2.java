package output.output2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import output.output2.consumer.Output2Consumer;
import output.output2.producer.Output2Producer;
/**
 * Reading from Middle Output Files, standardizing owner name and printing it to the final Output file.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class Output2 {
	private BufferedWriter writer;
	private int range;

	/**
	 * Initializing the writer and range.
	 * @param writer the writer used to print to final Output file.
	 * @param range the range of warranty year.
	 */
	public Output2(BufferedWriter writer, int range) {
		try {
			this.writer = writer;
			this.range = range;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Executing printing task 2 to file.
	 * 
	 * The process is split into 2 threads: Consumer and Producer. 
	 * - Producer will read records from Middle Output files and add it to the Blocking Queue.
	 * - Consumer will take records from Blocking Queue, standardize the Owner name and print it to final Output file.
	 */
	public void printTask2() {
		try {
			BlockingQueue<String> dataQueue = new ArrayBlockingQueue<String>(1000);
			BufferedReader readerList[] = new BufferedReader[range];
			for (int i=0; i<range; i++) {
				readerList[i] = new BufferedReader(new FileReader("MiddleOutput/MiddleOutput" + (range-i-1) + ".txt"), 8192*4);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(11);
			executorService.execute(new Output2Producer(dataQueue, readerList));
			executorService.execute(new Output2Consumer(dataQueue, writer));
			executorService.shutdown();
			while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} 

	}
}
