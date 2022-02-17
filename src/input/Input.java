package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import device.DeviceInterface;
import input.parser.Parser;
import input.parser.ParserInterface;

public class Input implements InputInterface {
	private String filename;
	private BufferedReader reader;
	private ParserInterface parser;

	public Input(String filename) {
		try {
			this.filename = filename;
			reader = new BufferedReader(new FileReader(this.filename), 16384);
			this.parser = new Parser();
		} catch (Exception e) {
			System.out.println(Paths.get(this.filename).toAbsolutePath());
			e.printStackTrace();
			System.exit(0);
		}
	}

	public List<DeviceInterface> readAll() {
		try {
			List<DeviceInterface> deviceList = new ArrayList<DeviceInterface>();
			String line;

			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				DeviceInterface device = parser.parseString(line);
				deviceList.add(device);
			}
			reader.close();
			return deviceList;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

}
