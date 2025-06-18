import java.util.Scanner;

/**
 * Reads user input and delegates execution to the registered commands.
 * @author ujnaa
 */
public class CommandProcessor {
    private final Playlist playlist;
    private final Scanner scanner;
    private final Command[] commands;
    private static final String UNKNOWN_COMMAND_MESSAGE = "Unknown command.";

    /**
     * Creates a new processor with the given playlist and input scanner.
     *
     * @param playlist the playlist to operate on
     * @param scanner  the scanner to read user input from
     */
    public CommandProcessor(Playlist playlist, Scanner scanner) {
        this.playlist = playlist;
        this.scanner = scanner;
        this.commands = createCommands();
    }

    /**
     * Returns the supported command list.
     *
     * @return array of commands
     */
    private static Command[] createCommands() {
        return new Command[]{
            new AddCommand(),
            new NextCommand(),
            new PlayCommand(),
            new RemoveCommand(),
            new PeekCommand(),
            new ListCommand(),
            new SkipCommand(),
            new HistoryCommand(),
            new QuitCommand()
        };
    }



    /**
     * Starts reading commands from the user until the quit command is issued.
     */
    public void run() {
        boolean running = true;
        while (running) {
            String input = scanner.nextLine().trim();
            boolean handled = false;
            for (Command cmd : commands) {
                if (cmd.matches(input)) {
                    try {
                        cmd.execute(input, playlist);
                    } catch (RuntimeException e) {
                        System.out.println("\u26A0\uFE0F " + e.getMessage());
                    }
                    handled = true;
                    if (cmd instanceof QuitCommand) {
                        running = false;
                    }
                    break;
                }
            }
            if (!handled) {
                System.out.println("\u2753 " + UNKNOWN_COMMAND_MESSAGE);
            }
        }
    }
}
