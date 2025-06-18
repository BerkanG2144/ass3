package kastel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for converting text definitions into {@link Song} objects.
 * The accepted formats correspond to the regular expressions exposed by
 * this class so that command implementations can validate input.
 * @author ujnaa
 */
public final class SongParser {
    /**
     * Regular expression to parse song input with priority.
     * <p>
     * The expected format is:
     * {@code <id>:<artist>:<title>:<length>:<priority>}
     * where:
     * <ul>
     *   <li>{@code <id>} is a positive integer (song ID)</li>
     *   <li>{@code <artist>} is the artist name (no colons)</li>
     *   <li>{@code <title>} is the song title (no colons)</li>
     *   <li>{@code <length>} is the duration of the song in seconds</li>
     *   <li>{@code <priority>} is an integer between 0 (highest) and 5 (lowest)</li>
     * </ul>
     */
    public static final String WITH_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+):(\\d+)";

    /**
     * Regular expression to parse song input without priority (used in `next` command).
     * <p>
     * The expected format is:
     * {@code <id>:<artist>:<title>:<length>}
     * where:
     * <ul>
     *   <li>{@code <id>} is a positive integer (song ID)</li>
     *   <li>{@code <artist>} is the artist name (no colons)</li>
     *   <li>{@code <title>} is the song title (no colons)</li>
     *   <li>{@code <length>} is the duration of the song in seconds</li>
     * </ul>
     */
    public static final String WITHOUT_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+)";


    private static final Pattern WITH_PRIORITY_PATTERN =
            Pattern.compile(WITH_PRIORITY_REGEX);
    private static final Pattern WITHOUT_PRIORITY_PATTERN =
            Pattern.compile(WITHOUT_PRIORITY_REGEX);

    private static final String INVALID_SONG_FORMAT = "Invalid song format";
    private static final int GROUP_ID = 1;
    private static final int GROUP_ARTIST = 2;
    private static final int GROUP_TITLE = 3;
    private static final int GROUP_LENGTH = 4;
    private static final int GROUP_PRIORITY = 5;
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
        Matcher m = WITH_PRIORITY_PATTERN.matcher(input);
        if (!m.matches()) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
        int id = Integer.parseInt(m.group(GROUP_ID));
        String artist = m.group(GROUP_ARTIST).trim();
        String title = m.group(GROUP_TITLE).trim();
        int length = Integer.parseInt(m.group(GROUP_LENGTH));
        int priority = Integer.parseInt(m.group(GROUP_PRIORITY));
        return new Song(id, artist, title, length, priority);
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
        Matcher m = WITHOUT_PRIORITY_PATTERN.matcher(input);
        if (!m.matches()) {
            throw new IllegalArgumentException(INVALID_SONG_FORMAT);
        }
        int id = Integer.parseInt(m.group(GROUP_ID));
        String artist = m.group(GROUP_ARTIST).trim();
        String title = m.group(GROUP_TITLE).trim();
        int length = Integer.parseInt(m.group(GROUP_LENGTH));
        return new Song(id, artist, title, length, DEFAULT_PRIORITY);
    }
}
