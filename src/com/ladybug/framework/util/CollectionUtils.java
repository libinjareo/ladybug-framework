package com.ladybug.framework.util;

import java.util.Collection;

/**
 * 针对于Collection的工具类
 * 
 * @author james.li
 *
 */
public class CollectionUtils {
	/**
	 * Return <code>true</code> if the supplied Collection is <code>null</code>
	 * or empty. Otherwise, return <code>false</code>.
	 * @param collection the Collection to check
	 * @return whether the given Collection is empty
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
}
