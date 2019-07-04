package zhy.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhy.blog.dao.IFriendLinkDao;
import zhy.blog.entity.FriendLink;
import zhy.blog.util.Status;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class FriendLinkController extends BaseController {
	private final IFriendLinkDao friendLinkDao;
	
	@Autowired
	public FriendLinkController(IFriendLinkDao friendLinkDao) {
		this.friendLinkDao = friendLinkDao;
	}
	
	@RequestMapping(value = "/friend", method = GET)
	public Object all() {
		return exceptionWrap(() -> friendLinkDao
									   .find(null).stream()
									   .map(f -> f.setInfo(null))
									   .sorted(Comparator.comparingInt(FriendLink::getStage))
									   .collect(Collectors.toList()));
	}
	
	@RequestMapping(value = "/friend/{id}", method = PATCH)
	public Object update(@PathVariable int id,
						 @RequestParam String url,
						 @RequestParam String name,
						 @RequestParam String tag,
						 @RequestParam String webName,
						 @RequestParam(defaultValue = Integer.MIN_VALUE + "") int stage,
						 @RequestParam(defaultValue = "") String info) {
		return exceptionWrap(u -> {
			FriendLink link = friendLinkDao.get(id);
			Objects.requireNonNull(link)
				.setUrl(url)
				.setName(name)
				.setWebName(webName)
				.setTag(tag)
				.setInfo(info)
				.setStage(stage)
				.assertValid()
				.setUpdateDate(new Date());
			friendLinkDao.update(link);
		});
	}
	
	
	@RequestMapping(value = "/friend/{id}", method = DELETE)
	public Object delete(@PathVariable int id) {
		return exceptionWrap(u -> {
			friendLinkDao.delete(id);
		});
	}
	
	@RequestMapping(value = "/friend", method = POST)
	public Object add(@RequestParam String url,
					  @RequestParam String name,
					  @RequestParam String tag,
					  @RequestParam String webName,
					  @RequestParam(defaultValue = "") String info,
					  @RequestParam(defaultValue = "" + Integer.MIN_VALUE) int stage) {
		return exceptionWrap(u -> {
			FriendLink link = (FriendLink) new FriendLink()
											   .setUrl(url)
											   .setWebName(webName)
											   .setInfo(info)
											   .setName(name)
											   .setTag(tag)
											   .setStage(stage)
											   .assertValid()
											   .setCreateDate(new Date())
											   .setUpdateDate(new Date())
											   .setStatus(Status.NORMAL);
			friendLinkDao.insert(link);
		});
	}
}
