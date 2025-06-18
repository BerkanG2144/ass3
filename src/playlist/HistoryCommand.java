package playlist;

/**
 * Handles the "history" command that prints played songs.
 * @author ujnaa
 */
public class HistoryCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equals("history");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        playlist.history();
    }
}
