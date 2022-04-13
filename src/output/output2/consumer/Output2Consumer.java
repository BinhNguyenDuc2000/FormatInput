package output.output2.consumer;

import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer will take records from Blocking Queue, standardize the Owner name and print it to final Output file.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class Output2Consumer implements Runnable{
	private final BlockingQueue<String> dataQueue;
	private BufferedWriter writer;

	public Output2Consumer(BlockingQueue<String> dataQueue, BufferedWriter writer) {
		this.dataQueue = dataQueue;
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
					String parts[] = message.split(",");
					parts[2] = standardizedOwner(parts[2]);
					message = String.join(",", parts);
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
	
	public String standardizedOwner(String owner) {
		owner = owner.trim();

		StringBuilder temp = new StringBuilder(String.valueOf(Character.toUpperCase(owner.charAt(0))));
		for (int i=1; i < owner.length(); i++) {
			if (owner.charAt(i-1) == ' ') {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp.append(Character.toUpperCase(owner.charAt(i)));
			}
			else {
				if (Character.isAlphabetic(owner.charAt(i)))
					temp.append(Character.toLowerCase(owner.charAt(i)));
				else {
					temp.append(' ');
				}
			}
		}
		return temp.toString();
	}
}
