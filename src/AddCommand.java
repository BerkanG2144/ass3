import java.util.regex.Pattern;

/**
 * Handles the "add" command that inserts a song into the playlist.
 * @author ujnaa
 */
public class AddCommand implements Command {
    private static final Pattern PATTERN = Pattern.compile("add\\s+" + SongParser.WITH_PRIORITY_REGEX);

    @Override
    public boolean matches(String input) {
        return PATTERN.matcher(input).matches();
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int idx = input.indexOf(' ');
        if (idx == -1) {
            throw new IllegalArgumentException("Invalid add command format");
        }
        String songDef = input.substring(idx + 1);
        playlist.addSong(SongParser.parseWithPriority(songDef));
    }
}
