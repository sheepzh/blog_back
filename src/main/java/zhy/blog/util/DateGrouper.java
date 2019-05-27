package zhy.blog.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DateGrouper<T> {
	public int[] groupByField(List<T> objects, int field,
							  Function<T, Date> dateMapper,
							  Function<T, Object> keyMapper) {
		
		if (objects == null || objects.isEmpty())
			return new int[0];
		
		HashMap<Integer, Set<Object>> temp = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		
		// Order by date desc firstly.
		objects.sort((o1, o2) -> -dateMapper.apply(o1).compareTo(dateMapper.apply(o2)));
		calendar.setTime(dateMapper.apply(objects.get(0)));
		ignore(calendar, field);
		Calendar objectCalendar = Calendar.getInstance();
		final int[] length = {0};
		objects.forEach(a -> {
			objectCalendar.setTime(dateMapper.apply(a));
			length[0] = calendar.get(field) - objectCalendar.get(field);
			Set<Object> set = temp.getOrDefault(length[0], new HashSet<>());
			set.add(keyMapper.apply(a));
			temp.put(length[0], set);
		});
		
		int[] result = new int[length[0] + 1];
		temp.forEach((k, v) -> result[k] = v.size());
		return result;
	}
	
	private void ignore(Calendar calendar, int field) {
		switch (field) {
			case Calendar.YEAR:
				calendar.set(Calendar.MONTH, 0);
			case Calendar.MONTH:
				calendar.set(Calendar.DATE, 0);
			case Calendar.DATE:
				calendar.set(Calendar.HOUR, 0);
			case Calendar.HOUR:
				calendar.set(Calendar.MINUTE, 0);
			case Calendar.MINUTE:
				calendar.set(Calendar.SECOND, 0);
			case Calendar.SECOND:
				calendar.set(Calendar.MILLISECOND, 0);
		}
	}
}
