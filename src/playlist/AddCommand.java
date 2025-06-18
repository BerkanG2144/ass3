package playlist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the "add" command that inserts a song into the playlist.
 * @author ujnaa
 */
public class AddCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("add\\s+(\\d+):([^:]+):([^:]+):(\\d+):(\\d+)");

    @Override
    public boolean matches(String input) {
        return PATTERN.matcher(input).matches();
    }

    @Override
    public void execute(String input, Playlist playlist) {
        Matcher m = PATTERN.matcher(input);
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));
            String artist = m.group(2).trim();
            String title = m.group(3).trim();
            int length = Integer.parseInt(m.group(4));
            int prio = Integer.parseInt(m.group(5));
            playlist.addSong(new Song(id, artist, title, length, prio));
        } else {
            throw new IllegalArgumentException("Invalid add command format");
        }
    }
}
