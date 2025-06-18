package kastel;

/**
 * Handles the "remove" command that deletes songs by id.
 * @author ujnaa
 */
public class RemoveCommand implements Command {
    private static final String PREFIX = "remove ";
    private static final int PREFIX_LENGTH = 7;
    private static final int NONE_REMOVED = 0;
    private static final String REMOVED_MESSAGE = "Removed ";
    private static final String REMOVED_SUFFIX = " songs.";
    @Override
    public boolean matches(String input) {
        return input.startsWith(PREFIX);
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int id = Integer.parseInt(input.substring(PREFIX_LENGTH).trim());
        int removed = playlist.removeById(id);
        if (removed > NONE_REMOVED) {
            System.out.println(REMOVED_MESSAGE + removed + REMOVED_SUFFIX);
        }
    }
}
