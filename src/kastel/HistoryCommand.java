package kastel;

/**
 * Handles the "history" command that prints played songs.
 * @author ujnaa
 */
public class HistoryCommand implements Command {
    private static final String COMMAND = "history";
    @Override
    public boolean matches(String input) {
        return input.equals(COMMAND);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        for (String s : playlist.history()) {
            System.out.println(s);
        }
    }
}
