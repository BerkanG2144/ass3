package kastel;

/**
 * Utility for converting text definitions into {@link Song} objects.
 * Command implementations rely on this class to validate the expected
 * input formats.
 * @author ujnaa
 */
public final class SongParser {
    private static final String INVALID_SONG_FORMAT = "Invalid song format";
    private static final int DEFAULT_PRIORITY = 0;

    private SongParser() {
    }

    /**
     * Parses a song definition that includes a priority.
     * <p>
     * Expected format: {@code id:artist:title:length:priority}
     *
     * @param input the string to parse
     * @return the resulting {@link Song}
     * @throws IllegalArgumentException if the input does not match the format
     */

    public static Song parseWithPriority(String input) {
        String[] parts = input.split(":", -1);
        if (parts.length != 5) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
        try {
            int id = Integer.parseInt(parts[0]);
            String artist = parts[1].trim();
            String title = parts[2].trim();
            int length = Integer.parseInt(parts[3]);
            int priority = Integer.parseInt(parts[4]);
            return new Song(id, artist, title, length, priority);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
    }

    /**
     * Parses a song definition without a priority. The song will receive
     * priority {@code 0}.
     * <p>
     * Expected format: {@code id:artist:title:length}
     *
     * @param input the string to parse
     * @return the resulting {@link Song}
     * @throws IllegalArgumentException if the input does not match the format
     */
    public static Song parseWithoutPriority(String input) {
        String[] parts = input.split(":", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
        try {
            int id = Integer.parseInt(parts[0]);
            String artist = parts[1].trim();
            String title = parts[2].trim();
            int length = Integer.parseInt(parts[3]);
            return new Song(id, artist, title, length, DEFAULT_PRIORITY);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
    }
}
