/**
 * Represents a command that can be executed by the playlist application.
 * @author ujnaa
 */
public interface Command {
    /**
     * Checks if the command can handle the given user input.
     *
     * @param input the complete input line
     * @return {@code true} if this command should handle the input
     */
    boolean matches(String input);

    /**
     * Executes the command using the provided playlist.
     *
     * @param input the complete input line
     * @param playlist the playlist instance to operate on
     */
    void execute(String input, Playlist playlist);
}
