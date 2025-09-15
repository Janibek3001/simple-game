import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SimpleGame sg = new SimpleGame();
        int[] locations = {3, 4, 5};
        sg.setLocationCells(locations);
        String testResult = "failed";

        while (true) {
            int guessUser = in.nextInt();
            String result = sg.CheckYourSelf(guessUser);
            System.out.println(result);
            if (result.equals("kill")) {
                testResult = "passed";
                break;
            }
        }
        System.out.println(testResult);
        in.close();
    }
}

class SimpleGame{
    private int[] locationCells;
    private int numberOfHits = 0;

    public void setLocationCells(int[] locs) {
        locationCells = locs;
    }

    public String CheckYourSelf(int guess) {
        String result = "miss";
        for (int i = 0; i < locationCells.length; i++){
            if(guess == locationCells[i]) {
                result = "hit";
                numberOfHits++;
                locationCells[i] = -1;
                break;
            }
        }
        if (numberOfHits == locationCells.length) {
            result = "kill";
        }
        return result;
    }
}