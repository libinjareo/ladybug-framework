package com.ladybug.framework.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Iterator adapter for enumeration.
 * 
 * @author james.li
 *
 * @param <E>
 */
public class EnumerationIterator<E> implements Iterator<E> {
	
	private final Enumeration<E> enumeration;
	
	public EnumerationIterator(Enumeration<E> enumeration){
		this.enumeration = enumeration;
	}

	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	public E next() {
		return enumeration.nextElement();
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
