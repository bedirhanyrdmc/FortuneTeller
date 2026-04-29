import java.util.ArrayList;
import java.util.Random;

/**
 * FortuneManager handles the storage and management of fortune strings.
 * It provides methods to retrieve, add, remove, and edit fortunes
 * using an ArrayList as the primary data structure.
 *
 * @author Han Yardimci
 * @author Yushi Kawashima
 * @date 04/21/2026
 */
public class FortuneManager {

    /** The list that stores all fortune strings. */
    private ArrayList<String> fortunes;

    /**
     * Constructs a FortuneManager and with 10 default fortunes.
     */
    public FortuneManager() {
        fortunes = new ArrayList<>();

        fortunes.add("You will have a great day.");
        fortunes.add("Success is coming your way.");
        fortunes.add("Be careful of new opportunities.");
        fortunes.add("A surprise is waiting for you.");
        fortunes.add("Your hard work will pay off soon.");
        fortunes.add("Someone is thinking about you.");
        fortunes.add("Good news will arrive shortly.");
        fortunes.add("You will learn something important today.");
        fortunes.add("A new friendship will form soon.");
        fortunes.add("Luck is on your side.");
    }

    /**
     * Returns a randomly selected fortune from the list.
     *
     * @return a random fortune string
     * @throws IllegalStateException if the fortune list is empty
     */
    public String getRandomFortune() {
        if (fortunes.isEmpty()) {
            throw new IllegalStateException("No fortunes available.");
        }
        Random rand = new Random();
        return fortunes.get(rand.nextInt(fortunes.size()));
    }

    /**
     * Adds a new fortune to the list.
     *
     * @param fortune the fortune string to add
     */
    public void addFortune(String fortune) {
        fortunes.add(fortune);
    }

    /**
     * Removes the fortune at the specified index.
     *
     * @param index the position of the fortune to remove
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void removeFortune(int index) {
        if (index < 0 || index >= fortunes.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        fortunes.remove(index);
    }

    /**
     * Replaces the fortune at the specified index with a new fortune string.
     *
     * @param index      the position of the fortune to edit
     * @param newFortune the new fortune string to replace the old one
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void editFortune(int index, String newFortune) {
        if (index < 0 || index >= fortunes.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        fortunes.set(index, newFortune);
    }

    /**
     * Returns a copy of the list of all fortunes.
     *
     * @return an ArrayList containing all current fortune strings
     */
    public ArrayList<String> getAllFortunes() {
        return new ArrayList<>(fortunes);
    }
}