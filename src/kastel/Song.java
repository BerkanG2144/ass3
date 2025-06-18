package kastel;

/**
 * Simple data holder representing a single track in the playlist.
 * A song has an ID, artist, title, length, priority and tracks remaining playback time.
 *
 * @author ujnaa
 */
public class Song {
    private static final String PEEK_FORMAT = "%05d:%s:%s:%d:%d";
    private static final String LIST_FORMAT = "%05d:%s:%s:%d";
    private final int id;
    private final String artist;
    private final String title;
    private final int length;
    private final int priority;
    private int remainingTime;


    /**
     * Constructs a song with the given properties.
     *
     * @param id the unique song identifier
     * @param artist the artist of the song
     * @param title the title of the song
     * @param length the duration in seconds
     * @param priority the priority from 0 (highest) to 5 (lowest)
     */
    public Song(int id, String artist, String title, int length, int priority) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.priority = priority;
        this.remainingTime = length;
    }

    /**
     * Gets the unique song ID.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the artist of this song.
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the title of this song.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the full length of this song in seconds.
     *
     * @return the song length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the priority level of this song.
     *
     * @return the priority (0 = highest, 5 = lowest)
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the remaining time for the song to finish.
     *
     * @return remaining playback time
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Updates the remaining play time of this song.
     *
     * @param remainingTime new remaining time in seconds
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Formats this song for output when peeking the current song.
     *
     * @return formatted song string with remaining time
     */

    public String toPeekString() {
        return String.format(
                PEEK_FORMAT,
                id,
                artist,
                title,
                length,
                remainingTime
        );
    }

    /**
     * Formats this song for output in lists and history.
     *
     * @return formatted song string without remaining time
     */
    public String toListString() {
        return String.format(LIST_FORMAT, id, artist, title, length);
    }

    /**
     * Checks if this song is equal to another.
     *
     * @param obj the object to compare
     * @return true if song content is identical
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Song)) {
            return false;
        }
        Song other = (Song) obj;
        return id == other.id
                && length == other.length
                && priority == other.priority
                && artist.equals(other.artist)
                && title.equals(other.title);
    }

    /**
     * Computes the hash code for this song.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Integer.hashCode(id);
        result = 31 * result + artist.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + Integer.hashCode(length);
        result = 31 * result + Integer.hashCode(priority);
        return result;
    }
}
