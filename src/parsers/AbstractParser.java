package parsers;

import java.util.HashMap;
import java.util.Map;

import exceptions.IllegalInputException;

public class AbstractParser {

	// input is without the method name (command)
	public static Map<String, String> splitParameters(final String input) throws IllegalInputException {
		//insert \\s* search for a 1 or more to search for one or more intervals
		String[] args = input.split("\\s*-");
         
		Map<String, String> argsMap = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			final String[] keyValuePair = args[i].split("=");

			if (keyValuePair.length != 2) {
				throw new IllegalInputException();
			}
             
			final String key = keyValuePair[0];
			final String value = keyValuePair[1];
			
			argsMap.put(key, value);
		}

		return argsMap;
	}}