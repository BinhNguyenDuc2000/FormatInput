package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import input.Input;
import log.Log;
import output.output1.Output1;
import output.output2.Output2;

/**
 * Controlling the flow of tasks.
 * @author Binh.NguyenDuc2000@gmail.com
 *
 */

public class FormatInputController {

	public static final Log log = new Log();
	private int range;
	private Timestamp startTime;
	private Timestamp stopTime;
	private Input input;
	private BufferedWriter writer;
	private Output1 output1;
	private Output2 output2;
	
	/**
	 * Initializing Input and Writer.
	 * @param inputFilename the input file name.
	 * @param outputFileName the output file name.
	 * @param range the range of warranty year.
	 */
	public FormatInputController(String inputFilename, String outputFileName, int range) {
		startTask("Initializing input(Getting file length and setting up readers)");
		this.range = range;
		input = new Input(inputFilename, range);
		endTask("Initializing input(Getting file length and setting up readers)");
		try {
			this.writer = new BufferedWriter(new FileWriter(outputFileName), 32768);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	/**
	 * Starting the logger.
	 */
	public void start() {
		log.startLogger();
		log.info("Size of input:" + JOptionPane.showInputDialog("Input size:"));
		String message = JOptionPane.showInputDialog("Log notes:");
		log.info(message);
	}
	
	/**
	 * Ending the logger and writer.
	 */
	public void end() {
		log.endLogger();
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Starting a task with a message and a timer.
	 * @param message task description.
	 */
	public void startTask(String message) {
		startTime = new Timestamp(System.currentTimeMillis());
		log.info("Starting task " + message);
	}
	
	/**
	 * Ending a task with a message and total time took.
	 * @param message task description.
	 */
	
	public void endTask(String message) {
		log.info("Ending task "+ message);
		stopTime = new Timestamp(System.currentTimeMillis());
		float runtime = ((float)(stopTime.getTime()-startTime.getTime())/1000);
		log.info("Task " + message + " took " + runtime + "s");
	}
	
	/**
	 * Executing task 1 which involves reading input file and printing sorter records list.
	 */
	public void task1() {	
		
		// Reading input file
		startTask("Reading input file");
		input.readAll();
		endTask("Reading input file");
		
		startTask("Printing task 1");
		output1 = new Output1(writer, range);
		output1.printTask1();
		endTask("Printing task 1");
	}
	
	/**
	 * Starting task 2 which involves standardizing records owner name and print the reverse records list.
	 */
	public void task2() {
		
		startTask("Printing to file task2");
		output2 = new Output2(writer, range);
		output2.printTask2();
		endTask("Printing to file task2");
	}

	public static void main(String[] args) {
		try {
			FormatInputController controller = new FormatInputController("Input/input.txt", "Output/output.txt", 100);
			controller.start();
			controller.task1();
			controller.task2();
			controller.end();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
