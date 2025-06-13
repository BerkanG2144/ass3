/**
 * Handles the "peek" command that prints the currently playing song.
 */
public class PeekCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equals("peek");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        Song s = playlist.peek();
        if (s != null) {
            System.out.println(s.toPeekString());
        }
    }
}
