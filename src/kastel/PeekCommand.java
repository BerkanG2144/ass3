package kastel;

/**
 * Handles the "peek" command that prints the currently playing song.
 * @author ujnaa
 */
public class PeekCommand implements Command {
    private static final String COMMAND = "peek";
    @Override
    public boolean matches(String input) {
        return input.equals(COMMAND);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        Song s = playlist.peek();
        if (s != null) {
            System.out.println(s.toPeekString());
        }
    }
}
