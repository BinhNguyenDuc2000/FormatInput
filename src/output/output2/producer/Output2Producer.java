package output.output2.producer;

import java.io.BufferedReader;
import java.util.concurrent.BlockingQueue;
/**
 * Producer will read records from Middle Output files and add it to the Blocking Queue.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */
public class Output2Producer implements Runnable{
	private final BlockingQueue<String> dataQueue;    
    private BufferedReader[] readerList;
    public Output2Producer(BlockingQueue<String> dataQueue, BufferedReader[] readerList) {
        this.dataQueue = dataQueue;
        this.readerList = readerList;
    }

    @Override
    public void run() {
        produce();
    }
    
    public void produce() {
    	String message;
		for (int i = 0; i < readerList.length; i++) {
			try {
				boolean running = true;
				while (running) {
					message = readerList[i].readLine();
					if (message != null) {
						dataQueue.put(message);
					} else {
						running = false;
						readerList[i].close();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
		}
		
		try {
			dataQueue.put("end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
}
