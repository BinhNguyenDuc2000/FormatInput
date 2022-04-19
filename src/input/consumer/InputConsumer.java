package input.consumer;

import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

/**
 * Taking records data from Blocking queue to print to Middle Output file.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class InputConsumer implements Runnable {
	private BlockingQueue<String> dataQueue;
	private BufferedWriter writer;

	public InputConsumer(BlockingQueue<String> dataQueue, BufferedWriter writer) {
		this.dataQueue = dataQueue;
		this.writer = writer;
	}

	@Override
	public void run() {
		consume();
	}

	/**
	 * Continue to read from blocking queue, stopping when 'end' message is received.
	 */
	private void consume() {
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
			e.printStackTrace();
			System.exit(1);
		} 
	}
}
