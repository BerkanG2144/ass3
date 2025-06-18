/**
 * Handles the "play" command that advances playback for a given time.
 * @author ujnaa
 */
public class PlayCommand implements Command {
    private static final String PLAY_PREFIX = "play ";
    private static final int PREFIX_LENGTH = 5;
    @Override
    public boolean matches(String input) {
        return input.startsWith(PLAY_PREFIX);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int seconds = Integer.parseInt(input.substring(PREFIX_LENGTH).trim());
        playlist.play(seconds);
    }
}
