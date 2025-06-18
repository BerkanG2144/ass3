/**
 * Handles the "skip" command that stops the current song.
 * @author ujnaa
 */
public class SkipCommand implements Command {
    private static final String COMMAND = "skip";
    @Override
    public boolean matches(String input) {
        return input.equals(COMMAND);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        playlist.skip();
    }
}
