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

public class Output2 implements Output2Interface {
	private BufferedWriter writer;
	private int range;

	public Output2(BufferedWriter writer, int range) {
		try {
			this.writer = writer;
			this.range = range;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
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
