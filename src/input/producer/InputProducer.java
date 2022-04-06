package input.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import controller.FormatInputController;

public class InputProducer implements Runnable {
    private final ArrayList<BlockingQueue<String>> dataQueueList;    
    private BufferedReader reader;
    public InputProducer(ArrayList<BlockingQueue<String>> dataQueueList, BufferedReader reader) {
        this.dataQueueList = dataQueueList;
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
					int index = message.charAt(message.length()-1) - 48;
					dataQueueList.get(index).put(message);
				}
				else {
					for (int index=0; index<dataQueueList.size();index++) {
						dataQueueList.get(index).put("end");
					}
					running = false;
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}  	
        }
        
        try {
			reader.close();
			FormatInputController.log.info("Input1 producer finished");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
       
    }
    
}