/**
 * Handles the "quit" command that ends the application.
 * @author ujnaa
 */
public class QuitCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equals("quit");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        System.out.println("\uD83D\uDC4B Bye!");
    }
}
