Version: 3.1.0 20160323

======================================================
==  READ THE ENTIRE FILE BEFORE DOING ANYTHING ELSE ==
======================================================

A simple case description:

A small zoo has a simple administration program about
their animals, cages and employees.

Animal are put in type dependent cages. For instance,
lions go into a lion cage, cats into a cat cage.
Animals are identified by a unique name (per animal type).
Cages needed will be made on demand.

Employees are identified by a unique employee number.
There are three different kinds of employees:
	- managers - manage other people (including themself)
	- administrators - do some bookkeeping
	- zoo keepers - take care of animals in a cage
The salary an employee earns depends on the kind of work
they do.  Consult the code in src/zoo/Employee for details.

A zoo keeper takes care of the animals in a single cage only.
Different zoo keepers could take care of the same kind of animals.

Managers represent a department. All types of employees belong
to a single department (so the zoo will have at least one manager).
Note: In the next version we'll probably add a director, who
will manage all managers. But this is not something for now.

All numbers/ages are integers.
Employee numbers are 3 digit values [100...999]
Names and types/kinds are non-empty strings.

Beware:
The current implementation will blindly accept wrong data
(which you can see for yourself by loading 'bad.txt').

=========================================================

For your assignment two different interfaces are provided.

- CLI.java
	An older command line interface.
	Only provided as a back-up.

- GUI.java
	This interface creates two windows:
  1. A menu window with various use-cases.
  	 When you close this window, the program stops.
  2. A window with the programs output.
     When you close this window, the program will
	 re-open it as needed.
     
The GUI interface uses the 'gui' package which is not part of this assignment.
Both interfaces use the 'util' package which is not part of this assignment.

The program is writen such that any exceptions are caught
and reported, but then the program will simply continue!


Below we may say 'Main.java', but please read this a 'GUI.java'
or 'CLI.java' depending on which interface you are using.

=========================================================

FIRST TASK.

- add pre-conditions (java 'assert' clauses) for early detection
  of possible mistakes, omissions and other errors, so the
  development team could repair them before proceeding with
  further development.
  To give you an example, this has already been done with
  the Animal class. It is up to you to do the same for:
  Cage and Employee. The Zoo class already has some asserts,
  but maybe a few more would be sensible.

SECOND TASK.

- The Cage publishes it's "TreeMap<Integer,Animal> animals"
  collection to the world. Therefor other classes can modify
  the contents without that cage knowning about those changes.
  This is a potential source for unpleasant surprises.
  Modify Cage and other classes in such a way that only a cage
  can/will manipulate that collection in safe ways.
  This implies that you will have to move some code from other
  classes to class Cage (add a few methods). While doing this,
  also add various 'asserts' to the new methods to detect/prevent
  improper actions, such as trying to add an animal twice to cage,
  or trying to remove a non-existing animal.

THIRD TASK.

- The Employee class uses "magic constants" to indicate the kind
  of employee an object represents. However, integer attributes
  can take on arbitrary (wrong) values. So this is not very
  robust.
- Also, various attributes and methods are only meaningfull
  for certain kinds of employees. So the given code is an
  example of vulnerable (and bad) design.
  What should have been done instead, is to make use of sub-typeing.

Your assignment:
  Write three new classes, e.g. Manager, Administrator and Keeper
  that inherit from Employee. Attributes and methods specific
  to some subtype should be moved to the class where they belong.
  Also, to ensure a Keeper (could also be called class ZooKeeper)
  will always have an associated Cage, add this as an extra parameter
  to the constructor of this new class. The existing setCage method
  than becomes redundant.

  Also, some classes should have their own versions of a method,
  e.g. the getSalary method (@Override).
	  Note: It is is considered bad practice for a super-class
	  to "know" about the particulars of the derived classes
	  because this "ties" all those classes together
	  [i.e. makes them much interdependent]).

  You should also consider updating the access grants (i.e. the
  public, protected and private qualifications).

  As a side effect, you will have to make minor changes to e.g.
  Main.addEmployee() en Main.loadFile().

  The Employee type constants (MANAGER, etc) have now become
  redundant and can be removed. The first parameter to the
  Employee constructor has also become unneeded. Likewise, the
  Employee.getType() method should be eliminated. In java you
  can check for the actual type of an object via 'instanceof',
  which is more reliable then a user writen method (which may
  contain errors).
  Again, as a side-effect, this results in minor changes in the
  Zoo class.
  
FINALLY (In case there is still some time left).

- The list of employees kept by a manager should become the "personal"
  property of a manager (similar to the second assigment)
- The structure and distribution of work between classes/objects/methods
  is not always as smart as can be. Some things are done by the wrong class.
- There may be a few pending TODO's at various places.

=========================================================

At the end of all the 'zoo' classes you'll find a 'saveFile' method
which is used to store zoo-data in a file (so it can be reloaded
when you run the program again later).
By the time you do the third part of the assignment you may have
to make minor changes there too.


=========================================================
Common Questions/Problems:
=========================================================

Q: How to enable 'asserts' in eclipse?
A:
- Run the program at least once (this makes a Launch configuration)
- Then go to:
    Project > Properties
	Run/Debug settings >
	    Select the Launch configuration, and edit it:
		Arguments tab
		    VM arguments:
			add: "-ea" or "-enableassertions"

Note: This was already arranged for in the given project files,
		but importing projects sometimes ignores that setting.


Alternative method (You can turn it into a "by default" setting)
Go to: Window > Preferences
    Select: Java > Installed RJE's
        Select: the default JRE (usually there is only one)
        	Edit: -> Default VM arguments
        		add: "-ea" or "-enableassertions"
From now on this will apply to ALL your projects.


=========================================================

Problem:
The program can not find the good.txt or bad.txt files !

Explanation:
Different IDEs and different OSes disagree about the "current
working directory" for your program.

Solution:
At the beginning of GUI.java (or CLI.java) you can find the list
of filenames.  You may have to add "src/" to each name, or maybe
"../" or "bin/" (e.g. "src/good.txt" or "../bad.txt").
At the beginning of 'main', you can find some code that has been
commented (PLEASE IGNORE ...etc). When you activate this code
(in eclipse: SHIFT-CONTROL-C) it will tell the name of the current
directory so you can figure out what text to add to those file
names.

=========================================================

Usefull links:
http://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
http://docs.oracle.com/javase/tutorial/java/concepts/inheritance.html
http://docs.oracle.com/javase/tutorial/java/IandI/override.html
http://docs.oracle.com/javase/tutorial/java/IandI/super.html

Extra:
http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

=========================================================

Note:
The 'compile', 'gendoc' and 'execute' files are not part of
your assignment. These are unix shell scripts I use often
rather than starting a slow and heavy IDE.

=========================================================

