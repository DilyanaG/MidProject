package menus;

import java.util.Map;

import controllers.ChannelController;
import controllers.VideoController;
import dataclasses.Video;
import enums.SortSearchBy;
import exceptions.DataBaseException;
import exceptions.IllegalInputException;
import parsers.GenericParser;
import parsers.VideoParser;

public class SearchMenu extends Menu{

	private GenericParser genericParser = GenericParser.getInstance();
	private VideoParser videoParser = VideoParser.getInstance();
	
	private VideoController videoController = VideoController.getInstance();
	private ChannelController channelController = ChannelController.getInstance();
	
	@Override
	protected String specialPresent() {
		final StringBuilder builder = new StringBuilder();
		builder.append(">OpenVideo -title=title_here\n");
		builder.append(">Homepage\n");
		return builder.toString();
	}

	@Override
	public Menu process(String input) throws IllegalInputException, DataBaseException {
		final String command = input.split(" ")[0].toLowerCase();// read first word from input
		final String args = input.substring(command.length()); // remove command

		final Map<String, String> argsMap = parseToMap(args);

		switch (command) {
		case "search":
			final SortSearchBy sortBy = SortSearchBy.resolve(argsMap);
			final String key = "tags";
			final String tags = genericParser.parseToString(argsMap, key);
			Menu searchMenu = videoController.search(tags, sortBy); 
			return searchMenu;
		case "openvideo":
			final Video video = videoParser.parse(argsMap);
			Menu videoMenu = videoController.openVideo(video.getTitle());
			return videoMenu;
		case "homepage":
			Menu homeMenu = channelController.homepage();
			return homeMenu;
		case "exit":
			Menu exitMenu = null;
			return exitMenu;

		default:
	       throw new IllegalInputException("INVALID INPUT !");
			
		}

	}

}
