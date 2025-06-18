/**
 * Handles the "remove" command that deletes songs by id.
 * @author ujnaa
 */
public class RemoveCommand implements Command {
    @Override
    public boolean matches(String input) {
        return input.startsWith("remove ");
    }

    @Override
    public void execute(String input, Playlist playlist) {
        int id = Integer.parseInt(input.substring(7).trim());
        int removed = playlist.removeById(id);
        if (removed > 0) {
            System.out.println("Removed " + removed + " songs.");
        }
    }
}
