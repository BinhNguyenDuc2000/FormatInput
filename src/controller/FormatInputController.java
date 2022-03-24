package controller;

import java.sql.Timestamp;
import java.util.List;

import javax.swing.JOptionPane;

import device.DeviceInterface;
import input.Input;
import input.InputInterface;
import log.Log;
import output.Output;
import output.OutputInterface;

/**
 * Controlling the flow of tasks.
 * @author ThinkPad
 *
 */

public class FormatInputController {

	private Log log;
	private Timestamp startTime;
	private Timestamp stopTime;
	private InputInterface input;
	private OutputInterface output;
	private List<DeviceInterface> deviceList;
	
	public FormatInputController(String inputFilename, String outputFilename) {
		startTime = new Timestamp(System.currentTimeMillis());
		log = new Log();		
		input = new Input(inputFilename);
		output = new Output(outputFilename);
		
	}
	
	public void start() {
		log.startLogger();
		log.info("Size of input:" + JOptionPane.showInputDialog("Input size:"));
		String message = JOptionPane.showInputDialog("Log notes:");
		log.info(message);
	}
	
	public void end() {
		log.endLogger();
		output.close();
	}
	
	public void startTask1() {
		startTime = new Timestamp(System.currentTimeMillis());
		log.info("Starting task 1");
	}
	
	public void endTask1() {
		log.info("Ending task 1");
		stopTime = new Timestamp(System.currentTimeMillis());
		float runtime = ((float)(stopTime.getTime()-startTime.getTime())/1000);
		log.info("Task 1 took " + runtime + "s");
	}
	
	public void startTask2() {
		startTime = new Timestamp(System.currentTimeMillis());
		log.info("Starting task 2");
	}
	
	public void endTask2() {
		log.info("Ending task 2");
		stopTime = new Timestamp(System.currentTimeMillis());
		float runtime = ((float)(stopTime.getTime()-startTime.getTime())/1000);
		log.info("Task 2 took " + runtime + "s");
	}
	
	public void task1() {
		startTask1();	
		// Reading input file
		log.info("Reading input file");
		deviceList = input.readAll();
		stopTime = new Timestamp(System.currentTimeMillis());
		float runtime = ((float)(stopTime.getTime()-startTime.getTime())/1000);
		log.info("Finished reading input file, took " + runtime + "s");
		
		// Sorting device list
		log.info("Sorting device list");
		deviceList.sort((device1, device2) -> device1.compareTo(device2));
		log.info("Printing to file task1");
		output.printTask1(deviceList);
		endTask1();
	}
	
	public void task2() {
		startTask2();
		log.info("Printing to file task2");
		output.printTask2(deviceList);
		endTask2();
	}

	public static void main(String[] args) {
		try {
			FormatInputController controller = new FormatInputController("Input/input1.txt", "Output/output1.txt");
			controller.start();
			controller.task1();
			controller.task2();
			controller.end();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
