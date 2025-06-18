import java.util.Scanner;

/**
 * Reads user input and delegates execution to the registered commands.
 * @author ujnaa
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
        this.commands = createCommands();
    }

    /**
     * Returns the supported command list.
     *
     * @return array of commands
     */
    private static Command[] createCommands() {
        Command[] cmds = new Command[9];
        cmds[0] = new AddCommand();
        cmds[1] = new NextCommand();
        cmds[2] = new PlayCommand();
        cmds[3] = new RemoveCommand();
        cmds[4] = new PeekCommand();
        cmds[5] = new ListCommand();
        cmds[6] = new SkipCommand();
        cmds[7] = new HistoryCommand();
        cmds[8] = new QuitCommand();
        return cmds;
    }



    /**
     * Starts reading commands from the user until the quit command is issued.
     */
    public void run() {
        boolean running = true;
        while (running) {
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
