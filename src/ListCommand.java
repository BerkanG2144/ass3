/**
 * Handles the "list" command that prints the playlist.
 * @author ujnaa
 */
public class ListCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.equals("list");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        for (String s : playlist.list()) {
            System.out.println(s);
        }
    }
}
