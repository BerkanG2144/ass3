public class Playlist {
    private static final int MAX_SONGS = 1000;

    private Song[][] queues;  // queues[priority][songs]
    private int[] sizes;     // sizes[priority] = current queue size
    private Song currentSong;
    private int remainingTime;
    private Song[] history;
    private int historySize;

    public Playlist() {
        queues = new Song[6][MAX_SONGS];  // 6 priority levels (0-5)
        sizes = new int[6];                      // Track sizes for each priority
        history = new Song[MAX_SONGS];    // History storage
        historySize = 0;
    }

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

    public void history() {
        for (int i = 0; i < historySize; i++) {
            System.out.println(history[i].toListString());
        }
    }

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

    public void removeFirstFromQueue(Song song) {
        int prio = song.getPriority();

        for (int i = 0; i < sizes[prio] - 1; i++) {
            queues[prio][i] = queues[prio][i + 1];
        }

        queues[prio][sizes[prio] - 1] = null;
        sizes[prio]--;
    }

    public void play(int seconds) {
        while (seconds > 0) {
            if (currentSong == null) {
                for (int prio = 0; prio < 6; prio++) {
                    if (sizes[prio] > 0) {
                        currentSong = queues[prio][0];
                        removeFirstFromQueue(currentSong);
                        break;
                    }
                }
                if (currentSong == null) return;
            }

            int remaining = currentSong.getRemainingTime();

            if (remaining > seconds) {
                currentSong.setRemainingTime(remaining - seconds);
                seconds = 0;
            } else {
                seconds -= remaining;
                addToHistory(currentSong);
                currentSong = null;
            }
        }
    }


    public void skip() {
        if (currentSong != null) {
            currentSong = null;
        }
    }

    /**
     * Adds a song to be played next (highest priority, after current song if playing).
     * @param song The song to add.
     */
    /**
     * Adds a song to be played next (highest priority, after current song if playing).
     * @param song The song to add.
     */
    public void addNext(Song song) {
        int prio = 0;

        if (sizes[prio] >= MAX_SONGS) return;

        int insertIndex = 0;

        // Wenn currentSong mit Prio 0 gerade läuft → insertIndex = 1
        if (currentSong != null && currentSong.getPriority() == prio) {
            insertIndex = 1;
        }

        for (int i = sizes[prio]; i > insertIndex; i--) {
            queues[prio][i] = queues[prio][i - 1];
        }

        queues[prio][insertIndex] = song;
        sizes[prio]++;
    }


    private Song[] expandArray(Song[] array) {
        Song[] newArray = new Song[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }


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
