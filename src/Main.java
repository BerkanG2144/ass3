import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Playlist playlist = new Playlist();
        Scanner scanner = new Scanner(System.in);

        System.out.println("▶️ Playlist ready. Type commands (add, next, play, peek, list, remove, skip, history, quit)");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("quit")) {
                System.out.println("👋 Bye!");
                break;
            }

            try {
                if (input.startsWith("add ")) {
                    String[] parts = input.substring(4).split(":");
                    int id = Integer.parseInt(parts[0].trim());
                    String artist = parts[1].trim();
                    String title = parts[2].trim();
                    int length = Integer.parseInt(parts[3].trim());
                    int prio = Integer.parseInt(parts[4].trim());
                    playlist.addSong(new Song(id, artist, title, length, prio));
                } else if (input.startsWith("next ")) {
                    String[] parts = input.substring(5).split(":");
                    int id = Integer.parseInt(parts[0].trim());
                    String artist = parts[1].trim();
                    String title = parts[2].trim();
                    int length = Integer.parseInt(parts[3].trim());
                    playlist.addNext(new Song(id, artist, title, length, 0));
                } else if (input.startsWith("play ")) {
                    int seconds = Integer.parseInt(input.substring(5).trim());
                    playlist.play(seconds);
                } else if (input.startsWith("remove ")) {
                    int id = Integer.parseInt(input.substring(7).trim());
                    playlist.removeById(id);
                } else if (input.equals("peek")) {
                    Song s = playlist.peek();
                    if (s != null) {
                        System.out.println(s.toPeekString());
                    }
                } else if (input.equals("list")) {
                    playlist.list();
                } else if (input.equals("skip")) {
                    playlist.skip();
                } else if (input.equals("history")) {
                    playlist.history();
                } else {
                    System.out.println("❓ Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
