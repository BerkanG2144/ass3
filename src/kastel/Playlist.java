package kastel;

/**
 * Manages all songs, queues and playback operations of the application.
 * @author ujnaa
 */
public class Playlist {
    /** initial capacity for queues and history */
    private static final int INITIAL_CAPACITY = 10;
    /** number of priority levels used by the playlist */
    private static final int NUM_PRIORITIES = 6;
    private static final int DEFAULT_PRIORITY = 0;
    private static final int NO_TIME_LEFT = 0;
    private static final String INVALID_PRIORITY_MSG = "Invalid priority: ";
    private static final int EXPANSION_FACTOR = 2;
    private static final int INITIAL_INDEX = 0;
    private static final int SHIFT = 1;

    private Song[][] queues;  // queues[priority][songs]
    private int[] sizes;     // sizes[priority] = current queue size
    private Song currentSong;
    private Song[] history;
    private int historySize;

    /**
     * Creates an empty playlist with six priority queues and an empty history.
     */
    public Playlist() {
        queues = new Song[NUM_PRIORITIES][INITIAL_CAPACITY];
        sizes = new int[NUM_PRIORITIES];
        history = new Song[INITIAL_CAPACITY];
        historySize = INITIAL_INDEX;
    }

    /**
     * Adds a song to the queue corresponding to its priority.
     *
     * @param song the song to enqueue
     * @throws IllegalArgumentException if the song has an invalid priority
     */
    public void addSong(Song song) {
        int priority = song.getPriority();
        if (priority < DEFAULT_PRIORITY || priority >= queues.length) {
            throw new IllegalArgumentException(INVALID_PRIORITY_MSG + priority);
        }
        if (sizes[priority] == queues[priority].length) {
            queues[priority] = expandArray(queues[priority]);
        }
        queues[priority][sizes[priority]++] = song;
    }

    private void addToHistory(Song song) {
        if (historySize == history.length) {
            history = expandArray(history);
        }
        history[historySize++] = song;
    }

    /**
     * Returns all songs that have been played so far in order of playtime.
     *
     * @return array of formatted song strings
     */
    public String[] history() {
        String[] result = new String[historySize];
        for (int i = 0; i < historySize; i++) {
            result[i] = history[i].toListString();
        }
        return result;
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

        for (int prio = 0; prio < NUM_PRIORITIES; prio++) {
            if (sizes[prio] > 0) {
                return queues[prio][INITIAL_INDEX];
            }
        }
        return null;
    }

    /**
     * Removes all songs with the given id from the queues and from the current
     * playback.
     *
     * @param id identifier of the song(s) to remove
     * @return removed amount
     */
    public int removeById(int id) {
        int amountRemoved = INITIAL_INDEX;

        if (currentSong != null && currentSong.getId() == id) {
            currentSong = null;
            amountRemoved++;
        }

        for (int prio = 0; prio < NUM_PRIORITIES; prio++) {
            int i = INITIAL_INDEX;
            while (i < sizes[prio]) {
                if (queues[prio][i].getId() == id) {
                    for (int j = i; j < sizes[prio] - SHIFT; j++) {
                        queues[prio][j] = queues[prio][j + SHIFT];
                    }
                    queues[prio][sizes[prio] - SHIFT] = null;
                    sizes[prio]--;
                    amountRemoved++;
                } else {
                    i++;
                }
            }
        }

        return amountRemoved;
    }

    /**
     * Removes the given song from the head of its priority queue.
     * @param song the song to be removed
     */
    public void removeFirstFromQueue(Song song) {
        int prio = song.getPriority();

        for (int i = 0; i < sizes[prio] - SHIFT; i++) {
            queues[prio][i] = queues[prio][i + SHIFT];
        }
        queues[prio][sizes[prio] - SHIFT] = null;
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
                for (int prio = 0; prio < NUM_PRIORITIES; prio++) {
                    if (sizes[prio] > 0) {
                        currentSong = queues[prio][INITIAL_INDEX];
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
                remainingSeconds = NO_TIME_LEFT;
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
            return;
        }

        for (int prio = 0; prio < NUM_PRIORITIES; prio++) {
            if (sizes[prio] > 0) {
                Song song = queues[prio][INITIAL_INDEX];
                removeFirstFromQueue(song);
                break;
            }
        }
    }

    /**
     * Inserts a song at the front of the highest priority queue so it will
     * play immediately after the current song (if any).
     *
     * @param song the song to schedule next
     */
    public void addNext(Song song) {
        int prio = DEFAULT_PRIORITY;

        if (sizes[prio] == queues[prio].length) {
            queues[prio] = expandArray(queues[prio]);
        }

        int insertIndex = DEFAULT_PRIORITY;

        for (int i = sizes[prio] - SHIFT; i >= insertIndex; i--) {
            queues[prio][i + SHIFT] = queues[prio][i];
        }

        queues[prio][insertIndex] = song;
        sizes[prio]++;
    }

    /**
     * Doubles the size of the given song array.
     */
    private Song[] expandArray(Song[] array) {
        Song[] newArray = new Song[array.length * EXPANSION_FACTOR];
        final int startIndex = 0;
        System.arraycopy(array, startIndex, newArray, startIndex, array.length);
        return newArray;
    }

    /**
     * Collects all songs in the playlist ordered by priority, including the
     * currently playing song if present.
     *
     * @return array of formatted song strings
     */
    public String[] list() {
        int total = 0;
        if (currentSong != null) {
            total++;
        }
        for (int i = 0; i < NUM_PRIORITIES; i++) {
            total += sizes[i];
        }

        String[] result = new String[total];
        int index = 0;
        for (int prio = 0; prio < NUM_PRIORITIES; prio++) {
            if (currentSong != null && currentSong.getPriority() == prio) {
                result[index++] = currentSong.toListString();
            }
            for (int i = 0; i < sizes[prio]; i++) {
                result[index++] = queues[prio][i].toListString();
            }
        }
        return result;
    }
}

