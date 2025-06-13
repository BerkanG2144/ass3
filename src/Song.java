public class Song {
    private final int id;
    private final String artist;
    private final String title;
    private final int length;
    private final int priority;
    private int remainingTime;

    public Song(int id, String artist, String title, int length, int priority){
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.priority = priority;
        this.remainingTime = length;
    }

    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String toPeekString() {
        return String.format("%05d:%s:%s:%d:%d", id, artist, title, length, remainingTime);
    }

    public String toListString() {
        return String.format("%05d:%s:%s:%d", id, artist, title, length);
    }

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
