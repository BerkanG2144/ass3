package kastel;

/**
 * Handles the "add" command that inserts a song into the playlist.
 * @author ujnaa
 */
public class AddCommand implements Command {
    private static final String PREFIX = "add ";
    private static final String INVALID_FORMAT_MESSAGE = "Invalid add command format";

    @Override
    public boolean matches(String input) {
        if (!input.startsWith(PREFIX)) {
            return false;
        }
        String songDef = input.substring(PREFIX.length());
        try {
            SongParser.parseWithPriority(songDef);
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
        playlist.addSong(SongParser.parseWithPriority(songDef));
    }
}
