import java.util.*;

public class Game {
    private GameHelper helper = new GameHelper();
    private ArrayList<Startup> startups = new ArrayList<Startup>();
    private int numOfGuess = 0;

    private void setUpGame() {
        Startup one = new Startup();
        one.setName("poniez");
        Startup two = new Startup();
        two.setName("hacqi");
        Startup three = new Startup();
        three.setName("cabista");

        startups.add(one);
        startups.add(two);
        startups.add(three);

        for (Startup startup : startups){
            ArrayList<String> newLocation = helper.placeStartup(3);
            startup.setLocationCells(newLocation);
        }
    }
    private void startPlaying() {
        while (!startups.isEmpty()){
            String userGuess = helper.getUserInput("Enter a guess: ");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess){
        numOfGuess++;
        String result = "miss";
        for (Startup startupToTest : startups) {
            result = startupToTest.checkYourself(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                startups.remove(startupToTest);
                break;
            }
        } // close for
        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("All Startups are dead! Your stock is now worthless");
        if (numOfGuess <= 18) {
            System.out.println("It only took you " + numOfGuess + " guesses.");
            System.out.println("You got out before your options sank.");
        } else {
            System.out.println("Took you long enough. " + numOfGuess + " guesses.");
            System.out.println("Fish are dancing with your options");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setUpGame();
        game.startPlaying();
    }
}

class Startup {
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList<String> locs) {
        locationCells = locs;
    }

    public void setName(String name) {
        this.name = name;
    }

    String checkYourself(String guess) {
        String result = "miss";
        int index = locationCells.indexOf(guess);

        if (index >= 0) {

            locationCells.remove(index);

            if (locationCells.isEmpty()){
                result = "kill";
            } else {
                result = "hit";
            }
        }
        return result;
    }
}

class GameHelper{
    private static final String ALPHABET = "abcdefg";
    private static final int GRIF_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;
    static final int HORIZONTAL_INCREMENT = 1;
    static final int VERTICAL_INCREMENT = GRIF_LENGTH;

    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();
    private int startupCount = 0;

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    }

    public ArrayList<String> placeStartup (int startupSize) {
        int[] startupCoords = new int[startupSize];
        int attempts = 0;
        boolean success = false;

        startupCount++;
        int increment = getIncrement();

        while (!success & attempts++ < MAX_ATTEMPTS) {
            int location = random.nextInt(GRID_SIZE);

            for (int i = 0; i < startupCoords.length; i++) {
                startupCoords[i] = location;
                location += increment;
            }

            if (startupFits(startupCoords, increment)) {
                success = coordsAvailable(startupCoords);
            }
        }
        savePositionToGrid(startupCoords);
        ArrayList<String> alphaCells = convertCoordsToAlphaFormat(startupCoords);
        return alphaCells;
    }

    private boolean startupFits(int[] startupCoords, int increment) {
        int finalLocation = startupCoords[startupCoords.length - 1];
        if (increment == HORIZONTAL_INCREMENT) {
            return calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(finalLocation);
        } else {
            return finalLocation < GRID_SIZE;
        }
    }

    private boolean coordsAvailable(int[] startupCoords) {
        for (int coord:startupCoords) {
            if (grid[coord] != 0) {
                return false;
            }
        }
        return true;
    }

    private void savePositionToGrid(int[] startupCoords) {
        for (int index : startupCoords) {
            grid[index] = 1;
        }
    }

    private ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        for (int index : startupCoords) { // for each grid coordinate
            String alphaCoords = getAlphaCoordsFromIndex(index); // turn it into an "a0" style
            alphaCells.add(alphaCoords); // add to a list
        }
        return alphaCells; // return the "a0"-style coords
    } // end convertCoordsToAlphaFormat
    private String getAlphaCoordsFromIndex(int index) {
        int row = calcRowFromIndex(index); // get row value
        int column = index % GRIF_LENGTH; // get numeric column value
        String letter = ALPHABET.substring(column, column + 1); // convert to letter
        return letter + row;
    } // end getAlphaCoordsFromIndex
    private int calcRowFromIndex(int index) {
        return index / GRIF_LENGTH;
    } // end calcRowFromIndex
    private int getIncrement() {
        if (startupCount % 2 == 0) { // if EVEN Startup
            return HORIZONTAL_INCREMENT; // place horizontally
        } else { // else ODD
            return VERTICAL_INCREMENT; // place vertically
        }
    }
}