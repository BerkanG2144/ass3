package playlist;

/**
 * Handles the "skip" command that stops the current song.
 * @author ujnaa
 */
public class SkipCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equals("skip");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        playlist.skip();
    }
}
