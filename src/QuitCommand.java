/**
 * Handles the "quit" command that ends the application.
 * @author ujnaa
 */
public class QuitCommand implements Command {
    private static final String COMMAND = "quit";
    private static final String BYE_MESSAGE = "Bye!";
    @Override
    public boolean matches(String input) {
        return input.equals(COMMAND);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        System.out.println("\uD83D\uDC4B " + BYE_MESSAGE);
    }
}
