package output.output1.consumer;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class Output1Consumer implements Runnable {
	private final ArrayList<BlockingQueue<String>> dataQueueList;
	private BufferedWriter writer;

	public Output1Consumer(ArrayList<BlockingQueue<String>> dataQueueList, BufferedWriter writer) {
		this.dataQueueList = dataQueueList;
		this.writer = writer;
	}

	@Override
	public void run() {
		consume();
	}

	public void consume() {
		try {

			Iterator<BlockingQueue<String>> iterator = dataQueueList.iterator();
			while (iterator.hasNext()) {
				String message;
				BlockingQueue<String> dataQueue = iterator.next();
				
				boolean running = true;
				while (running) {
					message = dataQueue.take();
					if (message.compareTo("end") != 0) {
						writer.write(message + "\n");
					} else {
						running = false;
					}

				}

			}
			writer.write("####\n");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
}
