package input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import controller.FormatInputController;
import input.consumer.InputConsumer;
import input.producer.InputProducer;

public class Input implements InputInterface {
	private BufferedReader reader;
	private int range;

	public Input(String filename, int range) {
		try {
			reader = new BufferedReader(new FileReader(filename));
			this.range = range;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Override
	public void readAll() {
		try {
			ArrayList<BlockingQueue<String>> dataQueueList = new ArrayList<>();
			BufferedWriter writerList[] = new BufferedWriter[range];
			for (int i=0; i<range; i++) {
				dataQueueList.add(new ArrayBlockingQueue<String>(1000));
				writerList[i] = new BufferedWriter(new FileWriter("MiddleOutput/MiddleOutput" + i + ".txt"), 8192 * 10);
			}
			InputProducer producer = new InputProducer(dataQueueList, reader);
			ExecutorService executorService = Executors.newFixedThreadPool(11);
			executorService.execute(producer);
			
			for (int i=0; i<range; i++) {
				executorService.execute(new InputConsumer(dataQueueList.get(i), writerList[i]));
			}
			executorService.shutdown();
			while (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES));
			FormatInputController.log.info("Input1 consumers finished");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
