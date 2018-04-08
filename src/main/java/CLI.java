// ===============================================================
//       IN PRINCIPE HOEF JE IN DEZE FILE NIETS TE VERANDEREN
//    TOTDAT JE NIEUWE CLASSES TOEVOEGT OF BESTAANDE VERWIJDERT
//				WAT PAS GEBEURT BIJ OPDRACHT 3
// ===============================================================
//
// Change  2.1:
// AKK 20150618 - removed lots of safeguard code
// Change  1.4.4:
// AKK 20150602 - removed various use-case limitations
// Changes 1.4.2:
// AKK 20140318 - disable some choices depending on presence of animals/employees
// Changes 1.4.1:
// AKK 20130515 - moved 'assert checked' message to the constructor
// AKK 20130515 - removed 'dierentuin' parameter from methods, use an attribute instead
// AKK 20130515 - un-static-ed all methods and created a Main instance instead
// AKK 20130405 - removed the AssertionError try/catch code again.
// Changes 1.3.3:
// AKK 20130309 - the scanner was never closed
// AKK 20130309 - removed the ansi-escape strings
// AKK 20120403 - AssertionError try/catch toegevoegd
// Changes 1.3.2:
// AKK 20120322 - eclipse cannot handle the ansi-escapes in the c_* strings
// AKK 20120322 - eclipse cannot handle the ansi-escapes in the asserts check
// AKK 20120322 - eclipse executes the program in the wrong directory,
//					adjusted the built-in save_file names.
// AKK 20120322 - when catching an IOException saveFile would always complain
//					about 'dierentuin.txt' rather than about the current save_file.
// AKK 20120322 - added example asserts to the internal methods.

// Voor load/save dierentuin
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
//import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import util.ReadInput;
import zoo.Administrator;
import zoo.Animal;
import zoo.Keeper;
import zoo.Manager;
import zoo.Zoo;
import zoo.Cage;
import zoo.Employee;


/**
 * De, textbased, gebruikersinterface (!!OBSOLETE!!)
 */
class CLI
{

	// --------------------------------------------------------------------
	// Dit programma kan snel gegevens inlezen uit een file
	// en ze daar later ook weer in opbergen;
	// - 'good.txt' bevat correcte informatie
	// (maar misschien dat die niet correct verwerkt wordt)
	// - 'bad.txt' bevat strijdige informatie
	// (die de dierentuin nu nog ten onrechte accepteert)

	// The names of the files to load/save
	static final String[] files = {
			"good.txt",			"bad.txt",
			"good-extra.txt",	"bad-extra.txt"
		};

	// Welke gebruikt wordt kan je hier kiezen:
	private static String de_savefile = files[0];	// good.txt
	// private static String de_savefile = files[1];	// bad.txt

	// Afhankelijk van de eclipse versie en je werkomgeving
	// moet er wel/niet een "bin/" prefix aan de naam van
	// het bestand worden toegevoegd.

	// Je kan de naam van de savefile ook via de programma
	// argumentenlijst door laten geven.
	// PS Er zijn ook nog twee "reserve" versies voor het geval dat je
	// de originele per ongeluk met foute informatie overschrijft.

	// The Main entry point
	public static void main(String[] args) {

//		/* PLEASE IGNORE THIS CODE */
//		try {
//			String current = new java.io.File(".").getCanonicalPath();
//			System.out.println("Current dir:" + current);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Current dir using System:" + System.getProperty("user.dir"));
//		/* END IGNORED CODE */

		// Is er een SaveFile naam argument meegegeven ?
		if (args.length > 0)
			de_savefile = args[0];
		new CLI("Artis");
	}

	// Instance variables
	Zoo dierentuin; // the zoo we manage

	// -----------------------

	private CLI(String naam) {

		// Staat java 'assert' wel aan ?
		if (!java_assert_enabled)
			System.err.println("\n\t** WARNING: JAVA ASSERTS ARE NOT ENABLED **\n");
		else
			System.out.println("OKE: java 'assert' is enabled\n");

		// Maak de dierentuin
		dierentuin = new Zoo(naam);
		System.out.println("Welkom bij dierentuin " + dierentuin.getName());

		// Aan het werk
		boolean stoppen = false;
		while (!stoppen) {
			// Druk het keuzemenu af
			System.out.println("\nKies een actie:");
			// begin
			System.out.println("  1\tdierentuin laden");
			// dieren
			System.out.println("  2\tdier toevoegen");
			if (dierentuin.hasAnimals()) // AKK: 20140318 added
				System.out.println("  3\tdier verwijderen");
			System.out.println("  4\toverzicht dieren");
			// werknemers
			System.out.println("  5\twerknemer toevoegen");
			if (dierentuin.hasEmployees()) // AKK: 20140318 added
				System.out.println("  6\twerknemer verwijderen");
			System.out.println("  7\toverzicht werknemers");
			System.out.println("  8\tsalaris kosten");
			// tenslotte
			// AKK: 20140318 test added
			if (dierentuin.hasAnimals() || dierentuin.hasEmployees())
				System.out.println("  9\tdierentuin opbergen");
			System.out.println("  0\tprogramma stoppen");

			// Vraag gebruiker om zijn keuze
			int keuze = ReadInput.getInt("Uw keuze");
			
			try {
				// Acties uitvoeren
				switch (keuze)
				{
				case 0:
					stoppen = true;
					break;
				// begin acties
				case 1:
					loadFile();
					break;
				// dier acties
				case 2:
					dierToevoegen();
					break;
				case 3:
					if (dierentuin.hasAnimals()) // AKK: 20140318 added
						dierVerwijderen();
					else
						System.err.println("actie 3 is nu niet geldig");
					break;
				case 4:
					dierentuin.showAnimals();
					break;
				// werknemers
				case 5:
					werknemerToevoegen();
					break;
				case 6:
					if (dierentuin.hasEmployees()) // AKK: 20140318 added
						werknemerVerwijderen();
					else
						System.err.println("actie 6 is nu niet geldig");
					break;
				case 7:
					dierentuin.showEmployees();
					break;
				case 8:
					dierentuin.showCosts();
					break;
				// tenslotte
				case 9:
					// AKK: 20140318 test added
					if (dierentuin.hasAnimals() || dierentuin.hasEmployees())
						saveFile();
					else
						System.err.println("actie 9 is nu niet geldig");
					break;
				// en de rest
				default:
					System.err.println("Onjuiste keuze gemaakt");
					break;
				} // end acties
			} catch(Throwable e) {
				// Kies hier waar de foutmelding wel/niet naar toe gaat
				printThrowable(e, System.out);
				//printThrowable(e, System.err);				
			}

		} // end while

		System.out.println("Tot ziens!");
	}
	
	// Internal function to print a partial stacktrace on some printstream
	private void printThrowable(Throwable e, PrintStream dest) {
		dest.println("------------");
		dest.println(e+"?");
		StackTraceElement[] s = e.getStackTrace();
		for (int i = 0; i < 3 && i < s.length; ++i) {
			if (i > 0) dest.print("  called from ");
			else dest.print("detected on ");
			dest.println(s[i]);
		}
		if (s.length > 3)
			dest.println("  called from ...");
		dest.println("------------\nProgram resumes ...");
	}

	// -------------------

	public void dierToevoegen() {
		assert dierentuin != null : "Cannot add an animal to a null zoo";
		System.out.println("Geef de soort, de naam en de leeftijd van het dier op");
		String dierSoort = ReadInput.getString("soort");
		String dierNaam = ReadInput.getString("naam");
		int dierLeeftijd = ReadInput.getInt("leeftijd");
		dierentuin.addAnimal(new Animal(dierNaam, dierSoort, dierLeeftijd));
	} // dierToevoegen

	public void dierVerwijderen() {
		assert dierentuin != null : "Cannot remove an animal from a null zoo";
		System.out.println("Geef de soort en de naam van het dier op");
		String dierSoort = ReadInput.getString("soort");
		String dierNaam = ReadInput.getString("naam");
		dierentuin.removeAnimal(dierSoort, dierNaam);
	} // dierVerwijderen

	// -------------------

	public void werknemerToevoegen() {
		assert dierentuin != null : "Cannot add an employee to a null zoo";
		// Wat voor soort werknemer moet het worden ?
		System.out.println("Geef de categorie op [1=manager,2=boekhouder,4=oppasser]");
		int werknemerSoort = ReadInput.getInt("categorie");
		// Verdere werknemer gegevens opvragen
		System.out.println("Geef het nummer, de naam en de leeftijd van de werknemer op");
		int werknemerNummer = ReadInput.getInt("nummer");
		String werknemerNaam = ReadInput.getString("naam");
		int werknemerLeeftijd = ReadInput.getInt("leeftijd");
		// Maak de goede soort werknemer
		Employee werknemer = null;
		switch (werknemerSoort)
		{
		case 1: // Manager
			werknemer = new Manager(werknemerNummer, werknemerNaam, werknemerLeeftijd);
//			werknemer = new Employee(Employee.MANAGER, werknemerNummer, werknemerNaam, werknemerLeeftijd);
			break;
		case 2: // Boekhouder
			werknemer = new Administrator(werknemerNummer, werknemerNaam, werknemerLeeftijd);
//			werknemer = new Employee(Employee.ADMINISTRATOR, werknemerNummer, werknemerNaam, werknemerLeeftijd);
			koppelAanManager(werknemer);
			break;
		case 4: // Oppasser
			// aanvullende informatie vragen
			String dierSoort = ReadInput.getString("welke diersoort");
			Cage kooi = dierentuin.findCage(dierSoort);
			if (kooi == null)
				kooi = dierentuin.makeCage(dierSoort);
			werknemer = new Keeper(werknemerNummer, werknemerNaam, werknemerLeeftijd, kooi);
//			werknemer = new Employee(Employee.ZOOKEEPER, werknemerNummer, werknemerNaam, werknemerLeeftijd);
//			werknemer.setCage(kooi);
			koppelAanManager(werknemer);
			break;
		}
		dierentuin.addEmployee(werknemerNummer, werknemer);
	} // werknemerToevoegen

	// intern hulpje
	private void koppelAanManager(Employee werknemer) {
		assert dierentuin != null : "Trying to attach an employee to a manager in a null zoo";
		assert werknemer != null : "Cannot attach a null employee to a manager";
		System.out.println("Bij welke manager werkt hij?");
		dierentuin.showManagers();
		int managerNummer = ReadInput.getInt("manager nummer");
		dierentuin.assignManager(managerNummer, werknemer);
	} // koppelAanManager

	public void werknemerVerwijderen() {
		assert dierentuin != null : "Cannot remove an employee from a null zoo";
		int werknemerNummer = ReadInput.getInt("nummer");
		dierentuin.removeEmployee(werknemerNummer);
	} // werknemerVerwijderen

	// ======================================================
	// De code hieronder kan een complete dierentuin opbergen
	// of weer inlezen van een file.
	// Dan hoef je tijdens het testen niet steeds alles opnieuw
	// in te voeren.

	// Save all data to a file.
	public void saveFile() {
		assert dierentuin != null : "Cannot save a null zoo";
		try {
			PrintWriter out = new PrintWriter(new FileWriter(de_savefile));
			System.out.println("Saving to " + de_savefile);
			dierentuin.saveFile(out);
			out.println("0");
			out.close();
		} catch (java.io.IOException ioe) {
			System.err.println("Oeps, kan " + de_savefile + " niet maken");
		}
	} // saveFile

	// Load saved data from a file.
	// Het eerste getal op een regel vertelt de actie.
	// De codes zijn net zoals in het menu hierboven:
	// 1=dier toevoegen, 2=werknemer toevoegen, 0=klaar.
	public void loadFile() {
		assert dierentuin != null : "Cannot load to a null zoo";
		try {
			Scanner scanner = new Scanner(new File(de_savefile));
			Pattern alfanum = Pattern.compile("\\w+"); // m.a.w. een "woord"

			System.out.println("Loading from " + de_savefile);

			int dieren = 0, werknemers = 0;

			// Zolang er nog iets te lezen valt
			while (scanner.hasNextLine()) {

				// Lees de actie code in
				int actie = scanner.nextInt();
				switch (actie)
				{

				case 0: // Klaar
					scanner.close();
					System.out.println(dieren + " dieren en " + werknemers + " werknemers ingelezen");
					return;

				case 1: { // Dier toevoegen
					// lees dier gegevens in
					String soort = scanner.next(alfanum);
					String naam = scanner.next(alfanum);
					int leeftijd = scanner.nextInt();
					dierentuin.addAnimal(new Animal(soort, naam, leeftijd));
					++dieren;
				}
					break; // dier toevoegen

				case 2: { // Medewerker toevoegen
					// lees medewerker gegevens in
					int categorie = scanner.nextInt();
					int nummer = scanner.nextInt();
					String naam = scanner.next(alfanum);
					int leeftijd = scanner.nextInt();
					Employee werknemer = null;
					switch (categorie)
					{

					case 1: { // Manager maken
						werknemer = new Manager(nummer, naam, leeftijd);
//						werknemer = new Employee(Employee.MANAGER, nummer, naam, leeftijd);
						}
						break;

					case 2: { // Boekhouder maken
						werknemer = new Administrator(nummer, naam, leeftijd);
//						werknemer = new Employee(Employee.ADMINISTRATOR, nummer, naam, leeftijd);
						// lees nummer van zijn baas in
						int baas = scanner.nextInt();
						dierentuin.assignManager(baas, werknemer);
						}
						break;

					case 4: { // Oppasser maken
						// lees de diersoort in van zijn kooi
						String soort = scanner.next(alfanum);
						Cage kooi = dierentuin.findCage(soort);
						if (kooi == null) kooi = dierentuin.makeCage(soort);
						werknemer = new Keeper(nummer, naam, leeftijd, kooi);
//						werknemer = new Employee(Employee.ZOOKEEPER, nummer, naam, leeftijd);
//						werknemer.setCage(kooi);
						// lees nummer van zijn baas in
						int baas = scanner.nextInt();
						dierentuin.assignManager(baas, werknemer);
						}
						break;

					default: // niet bestaande werknemer code
						System.err.println("Bogus categorie " + categorie + " gevonden!");
						scanner.close();
						return;

					} // categorie
					dierentuin.addEmployee(nummer, werknemer);
					++werknemers;
				}
					break; // medewerker toevoegen

				default: // niet bestaande actie code gevonden
					System.err.println("Bogus actie " + actie + " gevonden!");
					scanner.close();
					return;

				} // acties
			} // while input
			scanner.close();
			System.err.println("Unexpected EOF!");
		} catch (java.io.FileNotFoundException fnfe) {
			System.err.println("File " + de_savefile + " niet gevonden");
		} catch (java.util.NoSuchElementException nsee) {
			System.err.println("Foute invoer gevonden");
		}
	} // loadFile

	// ==========================================
	// Code to check the 'asserts-enabled' status
	public static boolean java_assert_enabled = false;
	static {
		assert java_assert_enabled = true; // mis-using a side-effect here !
	}
}
