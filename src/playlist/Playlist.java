package playlist;

/**
 * Manages all songs, queues and playback operations of the application.
 * @author ujnaa
 */
public class Playlist {
    /** maximum number of songs for history and initial queue sizes */
    private static final int MAX_SONGS = 1000;

    private Song[][] queues;  // queues[priority][songs]
    private int[] sizes;     // sizes[priority] = current queue size
    private Song currentSong;
    private int remainingTime;
    private Song[] history;
    private int historySize;

    /**
     * Creates an empty playlist with six priority queues and an empty history.
     */
    public Playlist() {
        queues = new Song[6][MAX_SONGS];  // 6 priority levels (0-5)
        sizes = new int[6];               // track sizes for each priority
        history = new Song[MAX_SONGS];    // history storage
        historySize = 0;
    }

    /**
     * Adds a song to the queue corresponding to its priority.
     *
     * @param song the song to enqueue
     * @throws IllegalArgumentException if the song has an invalid priority
     */
    public void addSong(Song song) {
        int priority = song.getPriority();
        // ensure priority is within valid range before accessing arrays
        if (priority < 0 || priority >= queues.length) {
            throw new IllegalArgumentException("Invalid priority: " + priority);
        }
        if (sizes[priority] == queues[priority].length) {
            queues[priority] = expandArray(queues[priority]);
        }
        queues[priority][sizes[priority]++] = song;
    }

    private void addToHistory(Song song) {
        if (historySize < MAX_SONGS) {
            history[historySize] = song;
            historySize++;
        }
    }

    /**
     * Prints all songs that have been played so far in order of playtime.
     */
    public void history() {
        for (int i = 0; i < historySize; i++) {
            System.out.println(history[i].toListString());
        }
    }

    /**
     * Returns the song that would currently be played without removing it.
     *
     * @return the current song or the next queued song, or {@code null} if none
     */
    public Song peek() {
        if (currentSong != null && currentSong.getRemainingTime() > 0) {
            return currentSong;
        }

        for (int prio = 0; prio < 6; prio++) {
            if (sizes[prio] > 0) {
                return queues[prio][0];
            }
        }
        return null;
    }

    /**
     * Removes all songs with the given id from the queues and from the current
     * playback.
     *
     * @param id identifier of the song(s) to remove
     */
    public void removeById(int id) {
        int amountRemoved = 0;

        if (currentSong != null && currentSong.getId() == id) {
            currentSong = null;
            amountRemoved++;
        }

        for (int prio = 0; prio < 6; prio++) {
            int i = 0;
            while (i < sizes[prio]) {
                if (queues[prio][i].getId() == id) {
                    for (int j = i; j < sizes[prio] - 1; j++) {
                        queues[prio][j] = queues[prio][j + 1];
                    }
                    queues[prio][sizes[prio] - 1] = null;
                    sizes[prio]--;
                    amountRemoved++;
                } else {
                    i++;
                }
            }
        }

        if (amountRemoved > 0) {
            System.out.println("Removed " + amountRemoved + " songs.");
        }
    }

    /**
     * Removes the given song from the head of its priority queue.
     * @param song the song to be removed
     */
    public void removeFirstFromQueue(Song song) {
        int prio = song.getPriority();

        for (int i = 0; i < sizes[prio] - 1; i++) {
            queues[prio][i] = queues[prio][i + 1];
        }

        queues[prio][sizes[prio] - 1] = null;
        sizes[prio]--;
    }

    /**
     * Advances playback by the given number of seconds, switching songs as
     * necessary and moving finished songs to the history.
     *
     * @param seconds number of seconds to play
     */
    public void play(int seconds) {
        int remainingSeconds = seconds;
        while (remainingSeconds > 0) {
            if (currentSong == null) {
                for (int prio = 0; prio < 6; prio++) {
                    if (sizes[prio] > 0) {
                        currentSong = queues[prio][0];
                        removeFirstFromQueue(currentSong);
                        break;
                    }
                }
                if (currentSong == null) {
                    return;
                }
            }

            int remaining = currentSong.getRemainingTime();

            if (remaining > remainingSeconds) {
                currentSong.setRemainingTime(remaining - remainingSeconds);
                remainingSeconds = 0;
            } else {
                remainingSeconds -= remaining;
                addToHistory(currentSong);
                currentSong = null;
            }
        }
    }


    /**
     * Stops the current song without adding it to the history.
     */
    public void skip() {
        if (currentSong != null) {
            currentSong = null;
        }
    }

    /**
     * Inserts a song at the front of the highest priority queue so it will
     * play immediately after the current song (if any).
     *
     * @param song the song to schedule next
     */
    public void addNext(Song song) {
        int prio = 0;

        if (sizes[prio] >= MAX_SONGS) {
            return;
        }

        int insertIndex = 0;

        for (int i = sizes[prio] - 1; i >= insertIndex; i--) {
            queues[prio][i + 1] = queues[prio][i];
        }

        queues[prio][insertIndex] = song;
        sizes[prio]++;
    }


    /**
     * Doubles the size of the given song array.
     */
    private Song[] expandArray(Song[] array) {
        Song[] newArray = new Song[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }


    /**
     * Prints all songs in the playlist ordered by priority, including the
     * currently playing song if present.
     */
    public void list() {
        for (int prio = 0; prio < 6; prio++) {
            if (currentSong != null && currentSong.getPriority() == prio) {
                System.out.println(currentSong.toListString());
            }
            for (int i = 0; i < sizes[prio]; i++) {
                System.out.println(queues[prio][i].toListString());
            }
        }
    }



}
