/**
 * Handles the "play" command that advances playback for a given time.
 * @author ujnaa
 */
public class PlayCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.startsWith("play ");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int seconds = Integer.parseInt(input.substring(5).trim());
        playlist.play(seconds);
    }
}
