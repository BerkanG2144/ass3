package kastel;

/**
 * Handles the "next" command that schedules a song as the next track.
 * @author ujnaa
 */
public class NextCommand implements Command {
    private static final String PREFIX = "next ";
    private static final String INVALID_FORMAT_MESSAGE = "Invalid next command format";

    @Override
    public boolean matches(String input) {
        if (!input.startsWith(PREFIX)) {
            return false;
        }
        String songDef = input.substring(PREFIX.length());
        try {
            SongParser.parseWithoutPriority(songDef);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void execute(String input, Playlist playlist) {
        if (!matches(input)) {
            throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE);
        }
        String songDef = input.substring(PREFIX.length());
        playlist.addNext(SongParser.parseWithoutPriority(songDef));
    }
}
