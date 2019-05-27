package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IRequestInfoDao;
import zhy.blog.entity.RequestInfo;
import zhy.blog.util.DateGrouper;
import zhy.util.leveldb.query.Condition;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static zhy.util.leveldb.query.Operation.NUMBER_NOT_LOWER;

@RestController
public class VisitorController extends BaseController {
	private final IRequestInfoDao requestInfoDao;
	
	@Autowired
	public VisitorController(IRequestInfoDao requestInfoDao) {
		this.requestInfoDao = requestInfoDao;
	}
	
	@RequestMapping(value = "/visitor/quantity/{unit}/{group}", method = RequestMethod.GET)
	public Object get(@PathVariable("group") int group,
					  @PathVariable("unit") String unit) {
		return exceptionWrap(() -> {
			Calendar calendar = Calendar.getInstance();
			int field = Calendar.DATE;
			switch (unit) {
				case "day":
					field = Calendar.DATE;
					break;
				case "hour":
					field = Calendar.HOUR;
					break;
				case "minute":
					field = Calendar.MINUTE;
					break;
				case "month":
					field = Calendar.MONTH;
					break;
			}
			calendar.add(field, group);
			calendar.setTimeInMillis(calendar.getActualMinimum(field));
			Condition condition = new Condition("createDate", String.valueOf(calendar.getTimeInMillis()), NUMBER_NOT_LOWER);
			requestInfoDao.setAdditionalCondition(Collections.singletonList(condition));
			List<RequestInfo> requestInfos = requestInfoDao.find(null);
			DateGrouper<RequestInfo> grouper = new DateGrouper<>();
			return grouper.groupByField(requestInfos, field, RequestInfo::getCreateDate, RequestInfo::getIp);
		});
		
	}
}
