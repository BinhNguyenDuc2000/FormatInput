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
		log = new Log();
		startTask("Initializing input(Getting file length)");
		input = new Input(inputFilename);
		endTask("Initializing input(Getting file length)");
		startTask("Initializing output");
		output = new Output(outputFilename);
		endTask("Initializing output");
		
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
	
	public void startTask(String message) {
		startTime = new Timestamp(System.currentTimeMillis());
		log.info("Starting task " + message);
	}
	
	public void endTask(String message) {
		log.info("Ending task "+ message);
		stopTime = new Timestamp(System.currentTimeMillis());
		float runtime = ((float)(stopTime.getTime()-startTime.getTime())/1000);
		log.info("Task " + message + " took " + runtime + "s");
	}
	
	
	public void task1() {	
		
		// Reading input file
		startTask("Reading input file");
		deviceList = input.readAll();
		endTask("Reading input file");
		
		// Sorting device list
		startTask("Sorting device list");
		log.info("Sorting device list");
		deviceList.sort((device1, device2) -> device1.compareTo(device2));
		endTask("Sorting device list");
		
		startTask("Printing task 1");
		output.printTask1(deviceList);
		endTask("Printing task 1");
	}
	
	public void task2() {
		
		startTask("Printing to file task2");
		output.printTask2(deviceList);
		endTask("Printing to file task2");
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
