package tetris_game.util;

public class Utility {
    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
            {
                //Runtime.getRuntime().exec("cls"); // doesn't work on windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // works
            }
            else
            {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (final Exception e)
        {
            // Handle any exceptions.
        }
    }
}