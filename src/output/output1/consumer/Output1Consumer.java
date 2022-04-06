package output.output1.consumer;

import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

public class Output1Consumer implements Runnable {
	private final BlockingQueue<String> dataQueue;
	private BufferedWriter writer;

	public Output1Consumer(BlockingQueue<String> dataQueueList, BufferedWriter writer) {
		this.dataQueue = dataQueueList;
		this.writer = writer;
	}

	@Override
	public void run() {
		consume();
	}

	public void consume() {
		try {
			String message;
			boolean running = true;
			while (running) {
				message = dataQueue.take();
				if (message.compareTo("end") != 0) {
					writer.write(message + "\n");
				} else {
					running = false;
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
