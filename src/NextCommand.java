import java.util.regex.Pattern;

/**
 * Handles the "next" command that schedules a song as the next track.
 * @author ujnaa
 */
public class NextCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("next\\s+" + SongParser.WITHOUT_PRIORITY_REGEX);

    @Override
    public boolean matches(String input) {
        return PATTERN.matcher(input).matches();
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int idx = input.indexOf(' ');
        if (idx == -1) {
            throw new IllegalArgumentException("Invalid next command format");
        }
        String songDef = input.substring(idx + 1);
        playlist.addNext(SongParser.parseWithoutPriority(songDef));
    }
}
