import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for converting text definitions into {@link Song} objects.
 * The accepted formats correspond to the regular expressions exposed by
 * this class so that command implementations can validate input.
 */
public final class SongParser {
    public static final String WITH_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+):(\\d+)";
    public static final String WITHOUT_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+)";

    private static final Pattern WITH_PRIORITY_PATTERN =
            Pattern.compile(WITH_PRIORITY_REGEX);
    private static final Pattern WITHOUT_PRIORITY_PATTERN =
            Pattern.compile(WITHOUT_PRIORITY_REGEX);

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
            throw new IllegalArgumentException("Invalid song format");
        }
        int id = Integer.parseInt(m.group(1));
        String artist = m.group(2).trim();
        String title = m.group(3).trim();
        int length = Integer.parseInt(m.group(4));
        int priority = Integer.parseInt(m.group(5));
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
            throw new IllegalArgumentException("Invalid song format");
        }
        int id = Integer.parseInt(m.group(1));
        String artist = m.group(2).trim();
        String title = m.group(3).trim();
        int length = Integer.parseInt(m.group(4));
        return new Song(id, artist, title, length, 0);
    }
}
