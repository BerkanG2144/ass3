import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the "next" command that schedules a song as the next track.
 * @author ujnaa
 */
public class NextCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("next\\s+(\\d+):([^:]+):([^:]+):(\\d+)");

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
            playlist.addNext(new Song(id, artist, title, length, 0));
        } else {
            throw new IllegalArgumentException("Invalid next command format");
        }
    }
}
