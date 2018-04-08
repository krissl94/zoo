package zoo;

import java.util.TreeMap;
// for saveFile at the end
import java.io.PrintWriter;


/**
 * A cage for all animals of a specific kind.
 * 
 * @author R.Akkersdijk
 */
public class Cage
{
	// The kind of animals in this cage
	private final String the_kind;

	// The collection of animals in this cage
	private final TreeMap<String,Animal> the_animals;

	/**
	 * @param kind What kind of animals are allowed in this cage
	 */
	public Cage(String kind) {
		// Pre-condities (require):
		assert kind != null : "null animal kind";				// M
		assert !kind.isEmpty() : "empty animal kind";			// M
		
		the_kind = kind;
		the_animals = new TreeMap<String,Animal>();
		
		// Also see note Animal constructor.
		// Post-condities (ensure):
		assert the_kind != null : "the_kind still null";		// W
		assert !the_kind.isEmpty() : "the_kind still empty";	// W
		assert the_animals != null : "oops, forgot new";		// W
	}

	/**
	 * What kind of animals in this cage?
	 * 
	 * @return The animal kind
	 */
	public String getKind() { // Needed for Keeper.print()
		return the_kind;
	}

	/**
	 * To calculate the salary of a zoo keeper.
	 * 
	 * @return the number of animals in this cage
	 */
	public int size() {
		return the_animals.size();
	}

	/**
	 * Reveal the list of animals
	 * 
	 * @return the animal list
	 */
	@Deprecated
	public TreeMap<String,Animal> getAnimals() {
		return the_animals;
	}
	// Second assignment:
	// Exposing private attributes opens the door for unpleasant surprises.
	public void addAnimal(Animal animal) {
		assert animal != null : "null animal";
		assert animal.isKind(the_kind) : "animal does not belong in this cage";
		assert !hasAnimal(animal.getName()) : "animal already in cage";
		the_animals.put(animal.getName(), animal);
	}
	public boolean hasAnimal(String animalName) {
		assert animalName != null : "null animal name";
		assert !animalName.isEmpty() : "empty animal name";
		return the_animals.containsKey(animalName);
	}
	public Animal getAnimal(String animalName) {
		assert hasAnimal(animalName) : "animal not in cage";
		return the_animals.get(animalName);
	}
	public void removeAnimal(String animalName) {
		assert hasAnimal(animalName) : "animal not in cage";
		the_animals.remove(animalName);
	}
	public void printAnimals() {
		if (the_animals.isEmpty())
			System.out.println("\tcage is empty");
		else { // Print the list of animals
			System.out.println("kind\tname\tage");
			for (Animal animal : the_animals.values())
				animal.print();
		}
	}
	public boolean hasAnimals() {
		return the_animals.size() > 0;
	}
	// end new methods

	public void print() {
		System.out.println("Cage " + the_kind);
	}

	public String toString() {
		return "Cage " + the_kind;
	}

	// =====================================
	public void saveFile(PrintWriter out) {
		assert out != null : "null PrintWriter";		// M
		for (Animal animal : the_animals.values()) {
			animal.saveFile(out);
		}
	}


}
