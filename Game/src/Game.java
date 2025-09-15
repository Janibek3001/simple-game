import java.util.Scanner;
import java.util.Random;

public class Game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random r = new Random();
        SimpleGame sg = new SimpleGame();


        int num = r.nextInt(4);
        int[] locations = {num, num + 1, num + 2};
        sg.setLocationCells(locations);
        String testResult = "failed";
        int guessNumber = 0;
        boolean isAlive = true;


        while (isAlive) {
            int guessUser = in.nextInt();
            guessNumber++;
            String result = sg.CheckYourSelf(guessUser);
            System.out.println(result);
            if (result.equals("kill")) {
                testResult = "passed";
                isAlive = false;
            }
        }
        System.out.println(testResult);
        System.out.printf("You took %d guesses", guessNumber);
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