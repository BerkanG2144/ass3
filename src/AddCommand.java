import java.util.regex.Pattern;

/**
 * Handles the "add" command that inserts a song into the playlist.
 * @author ujnaa
 */
public class AddCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("add\\s+" + SongParser.WITH_PRIORITY_REGEX);
    private static final char SPACE = ' ';
    private static final int SUBSTRING_OFFSET = 1;
    private static final String INVALID_FORMAT_MESSAGE = "Invalid add command format";

    @Override
    public boolean matches(String input) {
        return PATTERN.matcher(input).matches();
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int idx = input.indexOf(SPACE);
        if (idx == -1) {
            throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE);
        }
        String songDef = input.substring(idx + SUBSTRING_OFFSET);
        playlist.addSong(SongParser.parseWithPriority(songDef));
    }
}
