package output.output2.consumer;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class Output2Consumer implements Runnable{
	private final ArrayList<BlockingQueue<String>> dataQueueList;
	private BufferedWriter writer;

	public Output2Consumer(ArrayList<BlockingQueue<String>> dataQueueList, BufferedWriter writer) {
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
						String parts[] = message.split(",");
						parts[2] = standardizedOwner(parts[2]);
						message = String.join(",", parts);
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
