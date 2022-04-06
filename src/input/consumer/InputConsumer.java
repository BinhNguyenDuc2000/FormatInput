package input.consumer;

import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

public class InputConsumer implements Runnable {
	private final BlockingQueue<String> dataQueue;
	private BufferedWriter writer;

	public InputConsumer(BlockingQueue<String> dataQueue, BufferedWriter writer) {
		this.dataQueue = dataQueue;
		this.writer = writer;
	}

	@Override
	public void run() {
		consume();
	}

	public void consume() {
		try {
			while (true) {
				String message;
				message = dataQueue.take();
				if (message.compareTo("end") != 0) {
					writer.write(message+"\n");
				}
				else {
					break;
				}
			}
			writer.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} 
	}
}
