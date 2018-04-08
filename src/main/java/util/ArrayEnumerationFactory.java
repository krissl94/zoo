package util;

import java.lang.reflect.Array;
import java.util.Enumeration;

/**
 * A class to convert an array into an Enumeration
 * 
 * Source: http://www.java2s.com/Code/Java/Collections-Data-Structure/TreatinganArrayasanEnumeration.htm
 * 
 * @author unkown
 */
final class ArrayEnumerationFactory {

	@SuppressWarnings("rawtypes")
	public static Enumeration makeEnumeration(final Object obj) {
		Class type = obj.getClass();
		if (!type.isArray()) {
			throw new IllegalArgumentException(obj.getClass().toString());
		} else {
			return (new Enumeration() {
				private int size = Array.getLength(obj);

				private int cursor;

				public boolean hasMoreElements() {
					return (cursor < size);
				}

				public Object nextElement() {
					return Array.get(obj, cursor++);
				}
			});
		}
	}

	/** Test code */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Enumeration e = makeEnumeration(args);
		while (e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}

		e = makeEnumeration(new int[]{1, 3, 4, 5});
		while (e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}

		try {
			e = makeEnumeration(new Double(Math.PI));
		} catch (IllegalArgumentException ex) {
			System.err.println("Can't enumerate that: " + ex.getMessage());
		}
	}
}
