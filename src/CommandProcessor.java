import java.util.Scanner;

/**
 * Reads user input and delegates execution to the registered commands.
 */
public class CommandProcessor {
    private final Playlist playlist;
    private final Scanner scanner;
    private final Command[] commands;

    /**
     * Creates a new processor with the given playlist and input scanner.
     *
     * @param playlist the playlist to operate on
     * @param scanner  the scanner to read user input from
     */
    public CommandProcessor(Playlist playlist, Scanner scanner) {
        this.playlist = playlist;
        this.scanner = scanner;
        this.commands = new Command[] {
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
        System.out.println("\u25B6\uFE0F Playlist ready. Type commands (add, next, play, peek, list, remove, skip, history, quit)");
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            boolean handled = false;
            for (int i = 0; i < commands.length; i++) {
                Command cmd = commands[i];
                if (cmd.matches(input)) {
                    try {
                        cmd.execute(input, playlist);
                    } catch (Exception e) {
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
                System.out.println("\u2753 Unknown command.");
            }
        }
    }
}
