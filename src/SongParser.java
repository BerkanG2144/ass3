public final class SongParser {
    public static final String WITH_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+):(\\d+)";
    public static final String WITHOUT_PRIORITY_REGEX = "(\\d+):([^:]+):([^:]+):(\\d+)";

    private static final java.util.regex.Pattern WITH_PRIORITY_PATTERN =
            java.util.regex.Pattern.compile(WITH_PRIORITY_REGEX);
    private static final java.util.regex.Pattern WITHOUT_PRIORITY_PATTERN =
            java.util.regex.Pattern.compile(WITHOUT_PRIORITY_REGEX);

    private SongParser() {
    }

    public static Song parseWithPriority(String input) {
        java.util.regex.Matcher m = WITH_PRIORITY_PATTERN.matcher(input);
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

    public static Song parseWithoutPriority(String input) {
        java.util.regex.Matcher m = WITHOUT_PRIORITY_PATTERN.matcher(input);
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
