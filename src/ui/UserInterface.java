package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import controllers.UserController;
import parsers.CommentParser;
import parsers.UserParser;
import parsers.VideoParser;

abstract class Menu {

	public String presnet() {
		final StringBuilder builder = new StringBuilder();
		builder.append("default");
		final String special = specialPresent();
		builder.append(special);
		builder.append("more defaults");
		return builder.toString();
	}

	protected abstract String specialPresent();

	public abstract Menu process(String input);

	protected Map<String, String> parseToMap(String input) { // Input without
																// command
		return AbstractParser.splitParameters(input);
	}

}

public class UserInterface {
	private Menu menu; // = new DefaultMenu();

	private final Scanner scanner = new Scanner(System.in);

	public void run() {
		try {
			runApplication();
			System.out.println("Good buy");
		} catch (Throwable t) {
			Logger.getGlobal().severe(t.getMessage() + " " + t.getStackTrace());
			System.out.println("Internal server error! Application will terminate. Sorry :(");
		} finally {
			scanner.close();
		}
	}

	public void runApplication() {
		while (menu != null) {
			final String presentation = menu.presnet();
			System.out.println(presentation);

			final String input = scanner.nextLine();
			menu = menu.process(input);
		}
	}
}

class UserMenu extends Menu {

	private UserParser parser = UserParser.getInstance();
	private UserController controller = UserController.getInstance();

	private VideoParser videoParser = VideoParser.getInstance();
	private CommentParser commentParser = CommentParser.getInstance();

	@Override
	protected String specialPresent() {
		final StringBuilder builder = new StringBuilder();
		builder.append("very special user menu");
		return builder.toString();
	}

	@Override
	public Menu process(String input) {
		final String command = input.split(" ")[0];// read first word from
													// input;
		final Map<String, String> argsMap = parseToMap(input); // input minus
																// command
		switch (command) {
		case "method-1":
			// final Video video = videoParser.parse(argsMap);
			// final Comment comment = commnetParser.parse(argsMap);
			// Menu newMenu = controller.writeCommentForVideo(comment, video);
			// return newMenu;
			break;

		default:
			break;
		}

		return null;
	}

}

class AbstractParser {

	public static Map<String, String> splitParameters(final String input) {
		String[] args = input.split(" -");

		Map<String, String> argsMap = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			final String[] keyValuePair = args[i].split("=");

			if (keyValuePair.length != 2) {
				// explosions
			}

			final String key = keyValuePair[0];
			final String value = keyValuePair[1];

			argsMap.put(key, value);
		}

		return argsMap;
	}}

/*

// YA CHANGES

public class UserInterface {

	public static final String SUCCESSFUL_SIGN_UP_MESSAGE = "YOU SUCCESSFULLY REGISTERED !!!";

	private UserController userController;
	private UserInterface onlineUser;
	private UserInterface offlineUser;
	private ChannelController channelController;
	private VideoController videoController;

	public UserInterface() {
		userController = UserController.getInstance();
		channelController = ChannelController.getInstance();
		videoController = VideoController.getInstance();
	}

	public void mainMenu() {

		this.getOnlineandOfflineUserIntefaces();
		System.out.println(" ________________YOUTUBE__________________");
		System.out.println("|>Login                                   |");
		System.out.println("|>SignUp                                  |");
		System.out.println("|>Search                                  |");
		System.out.println("|>Exit                                    |");
		System.out.println("|_________________________________________|");
		System.out.println("Enter command:");
		String choice = getStringFromKeyboard();

		while (true)
			switch (choice.toLowerCase()) {
			case "login": {
				this.loginMenu();
				return;
			}
			case "signup": {
				this.SignUpMenu();
				return;
			}
			case "search": {
				this.searchMenu();
				return;
			}
			case "exit": {
				//TODO
				return;
			}

			default: {
				System.out.println("WRONG COMMAND!!! TRY AGAIN!");
				System.out.println("Exit ->end the program!");
				System.out.println("Enter command:");
				choice = getStringFromKeyboard();
			}

			}

	}

	public void SignUpMenu() {
		System.out.println(" ______________SIGN UP______________");
		System.out.println("|Enter username:");
		String username = getStringFromKeyboard();
		System.out.println("|Enter pasword");
		String password = getStringFromKeyboard();
		System.out.println("|Enter email");
		String email = getStringFromKeyboard();
		if (userController.register(username, password, email)) {
			System.out.println(onlineUser.SUCCESSFUL_SIGN_UP_MESSAGE);
			this.loginMenu();
			return;
		}

		if (this.checkForYesOrNo(true)) {
			this.SignUpMenu();
		} else {
			mainMenu();
		}
	}

	public void loginMenu() {
		System.out.println(" _____________LOGIN______________");
		System.out.println("|Enter your username:");
		String username = getStringFromKeyboard();
		System.out.println("|Enter your password:");
		String password = getStringFromKeyboard();

		if (!this.userController.login(username, password)) {
			if (this.checkForYesOrNo(true)) {
				loginMenu();
			} else {
				mainMenu();
			}
		}

	}

	// TODO
	private void searchMenu() {
		// Rething menu logic into classes of UI and interfaces
	}

	public void channelMenu(Channel channel) {
		System.out.println(" __________CHANNEL '" + channel.getUser().getUserName().toUpperCase() + "'_______");
		channelPrintBar();
		System.out.println("|>EXIT");
		enterCommand(channel);
	}

	protected void channelPrintBar() {
		System.out.println("|>Search");
		System.out.println("|>Videos");
		System.out.println("|>Playlist");
		System.out.println("|>Followed");
	}

	protected void enterCommand(Channel channel) {
		while(true){ 
			System.out.println("Enter command:");
			String command = getStringFromKeyboard();
			if(giveCommandsFromChannel(command,channel)){
				break;
			}
			}
	}

	protected boolean giveCommandsFromChannel(String command,Channel channel) {

		switch (command) {
		case "search": {
         
			return true;
		}
		
		case "videos": {
			this.channelVideosMenu(channel);
			return true;
		}
		case "playlist": {
			
			return true;
		}
		case "followed": {
			return true;
		}
		
		case "exit": {
			return true;
		}

		default: {
			System.out.println("WRONG COMMAND!!! TRY AGAIN!");
			System.out.println("Exit ->end the program!");
			return false;
		}

		}
	}

	private void channelVideosMenu(Channel channel) {
		System.out.println("--------'"+channel.getUser().getUserName()+"' UPLOAD VIDEOS-----------");
		Map<Integer,Video> videos = videoController.giveVideosToChannel(channel);
		if(videos!=null&&!videos.isEmpty()){
		for(Map.Entry<Integer,Video> entry : videos.entrySet()){
			System.out.println(entry.getKey()+" : "+entry.getValue().getTitle());
		}
		System.out.println("Enter video id to open video: ");
		//TO DO validate
		int id=new Scanner(System.in).nextInt();
		this.videoMenu(videos.get(id));
		
		}else{
			System.out.println("CHANNEL DON'T HAVE A UPLOAD VIDEOS!");
		}
	}

	private void videoMenu(Video video) {
		 System.out.println("---------------------------");
		 System.out.println("title: "+video.getTitle());
         System.out.println("");		
	}

	private void addVideoMenu(Channel channel) {
		System.out.println("------ADD_NEW_VIDEO------");
		System.out.println("Enter video title:");
		String title = getStringFromKeyboard();
		System.out.println("Enter video url:");
		String url = getStringFromKeyboard();
		System.out.println("Do you want to add description[yes/no]:");
		String discription = "";
		if (checkForYesOrNo(false)) {
			discription = getStringFromKeyboard();
		}
		// TODO This should be the VideoParser
		// if (!.parse(url, channel.getUser().getUserName(), title,
		// discription)) {
		// if (checkForYesOrNo(true)) {
		// addVideoMenu(channel);
		// } else {
		// this.channelMenu(channel, true);
		// }
		// }
	}

	protected String getStringFromKeyboard() {
		Scanner scanner = new Scanner(System.in);
		return scanner.next().trim().toLowerCase();
	}

	public boolean checkForYesOrNo(boolean forData) {
		if (forData) {
			System.out.println("INCORRECTLY ENTERED DATA!");
		}
		System.out.println("DO YOU WANT TRY AGAIN!\nENTER [YES] or [NO]");

		while (true) {
			String choice = getStringFromKeyboard();
			switch (choice) {
			case "no":
				return false;
			case "yes":
				return true;
			default: {
				System.out.println("INCORRECT INPUT!ENTER AGAIN!");
				continue;
			}
			}
		}
	}

	private void getOnlineandOfflineUserIntefaces() {
		this.onlineUser = OnlineUserInterface.getInstance();
		this.offlineUser = OfflineUserInterface.getInstance();
	}
}
 */
