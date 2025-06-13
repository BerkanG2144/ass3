/**
 * Simple data holder representing a single track in the playlist.
 */
public class Song {
    private final int id;
    private final String artist;
    private final String title;
    private final int length;
    private final int priority;
    private int remainingTime;

    /**
     * Constructs a song with the given properties.
     */
    public Song(int id, String artist, String title, int length, int priority){
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.priority = priority;
        this.remainingTime = length;
    }

    /** @return the unique id of this song */
    public int getId() {
        return id;
    }

    /** @return the artist name */
    public String getArtist() {
        return artist;
    }

    /** @return the title of the song */
    public String getTitle() {
        return title;
    }

    /** @return length of the song in seconds */
    public int getLength() {
        return length;
    }

    /** @return priority level from 0 (highest) to 5 */
    public int getPriority() {
        return priority;
    }

    /** @return remaining play time for the current song */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Updates the remaining play time of this song.
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Formats this song for output when peeking the current song.
     */
    public String toPeekString() {
        return String.format("%05d:%s:%s:%d:%d", id, artist, title, length, remainingTime);
    }

    /**
     * Formats this song for output in lists and history.
     */
    public String toListString() {
        return String.format("%05d:%s:%s:%d", id, artist, title, length);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;
        Song other = (Song) obj;
        return id == other.id &&
                length == other.length &&
                priority == other.priority &&
                artist.equals(other.artist) &&
                title.equals(other.title);
    }

    /** {@inheritDoc} */
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
