package output.output2.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Output2Producer implements Runnable{
	private final BlockingQueue<String> dataQueue;    
    private BufferedReader reader;
    public Output2Producer(BlockingQueue<String> dataQueue, BufferedReader reader) {
        this.dataQueue = dataQueue;
        this.reader = reader;
    }

    @Override
    public void run() {
        produce();
    }
    
    public void produce() {
    	boolean running = true;
        while (running) {
            String message;
			try {
				message = reader.readLine();
				if (message != null) {
					dataQueue.put(message);
					Thread.sleep(10);
				}
				else {
					dataQueue.put("end");
					running = false;
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}  	
        }
        
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
       
    }
}
