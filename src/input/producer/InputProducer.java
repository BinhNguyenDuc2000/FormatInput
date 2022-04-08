package input.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import input.Input;

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
    	long readChars = 0;
        for ( ;readChars<Input.numberOfCharsPerThread; ) {
            String message;
			try {
				message = reader.readLine();
				if (message != null) {
					int index = message.charAt(message.length()-1) - 48;
					readChars += message.length() + 2;
					dataQueueList.get(index).put(message);
				}
				else {
					break;
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