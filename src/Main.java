import java.util.Scanner;

/**
 * Entry point of the playlist application.
 */
public class Main {
    /**
     * Starts the command processor loop.
     *
     * @param args ignored command line arguments
     */
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        Scanner scanner = new Scanner(System.in);
        CommandProcessor processor = new CommandProcessor(playlist, scanner);
        processor.run();
        scanner.close();
    }
}
