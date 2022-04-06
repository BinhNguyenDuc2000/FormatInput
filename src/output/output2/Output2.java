package output.output2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
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
			ArrayList<BlockingQueue<String>> dataQueueList = new ArrayList<>();
			BufferedReader readerList[] = new BufferedReader[range];
			for (int i=range-1; i > -1; i--) {
				dataQueueList.add(new ArrayBlockingQueue<String>(10000));
				readerList[range-i-1] = new BufferedReader(new FileReader("MiddleOutput/MiddleOutput" + i + ".txt"), 8192*4);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(11);
			for (int i=0; i < range; i++) {
				executorService.execute(new Output2Producer(dataQueueList.get(i), readerList[i]));
			}
			Output2Consumer consumer = new Output2Consumer(dataQueueList, writer);
			executorService.execute(consumer);
			executorService.shutdown();
			while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} 

	}
}
