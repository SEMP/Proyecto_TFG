package utilidades.util;

import java.util.Comparator;

public final class ComparadorAscendente<T extends Comparable<T>> implements Comparator<T>
{
	@Override
	public int compare(T obj1, T obj2)
	{
		if(obj1 == null && obj2 == null) return 0;
		if(obj1 == null && obj2 != null) return -1;
		if(obj1 != null && obj2 == null) return 1;
		
		return obj1.compareTo(obj2);
	}
}