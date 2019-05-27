package zhy.blog.util;

import zhy.blog.dao.leveldb.ArticleDao;
import zhy.blog.dao.leveldb.CommentDao;
import zhy.blog.dao.leveldb.FriendLinkDao;
import zhy.blog.dao.leveldb.GroupDao;
import zhy.blog.dao.leveldb.PoemDao;
import zhy.blog.dao.leveldb.RequestInfoDao;
import zhy.blog.dao.leveldb.StateDao;
import zhy.blog.entity.Article;
import zhy.blog.entity.BaseEntity;
import zhy.blog.entity.Comment;
import zhy.blog.entity.GroupNode;
import zhy.blog.entity.Poem;
import zhy.blog.entity.RequestInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Operator of database
 *
 * @author zhy
 */
public class DbOperator {
	private static GroupDao groupDao = new GroupDao();
	private static ArticleDao articleDao = new ArticleDao();
	private static CommentDao commentDao = new CommentDao();
	private static PoemDao poemDao = new PoemDao();
	private static StateDao stateDao = new StateDao();
	private static FriendLinkDao friendLinkDao = new FriendLinkDao();
	private static RequestInfoDao requestInfoDao = new RequestInfoDao();
	
	public static void main(String[] a) throws ParseException {
//        Article article = new Article().setGroupId(1).setTitle("测试").setContent("测试内容\n\n测试内容");
//        articleDao.insertInitialized(article);
//
//        GroupNode node = new GroupNode().setLevel(1).setName("1").setTags(new String[]{});
//        groupDao.insertNormal(node);
//        Poem poem = new Poem().setTitle("测试").setContent("1\n\n2\n3\n555");
//        poemDao.insertInitialized(poem);
//		printComment();
//		requestInfoDao.delete(9);
		requestInfoDao.init();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
		Date date = simpleDateFormat.parse("20190515 112355");
		Calendar calendar = Calendar.getInstance();
		
		RequestInfo requestInfo = (RequestInfo) new RequestInfo().setIp("212313").setCreateDate(date).setStatus(Status.NORMAL);
		calendar.setTime(date);
		for (int i = 0; i < 10; i++) {
			calendar.add(Calendar.DATE, 1);
			Date date1 = calendar.getTime();
			requestInfo.setCreateDate(date1);
			requestInfoDao.insert(requestInfo);
		}
		List<RequestInfo> list = requestInfoDao.find(null);
		list.sort((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
//		list.forEach(mm -> System.out.println(simpleDateFormat.format(mm.getCreateDate())));
		for (int i : new DateGrouper<RequestInfo>().groupByField(list, Calendar.DATE, RequestInfo::getCreateDate, RequestInfo::getIp)) {
			System.out.print(i + "\t");
		}
//		printRequestInfo();
	}
	
	private static void printGroup() {
		List<GroupNode> result = groupDao.find(null);
		System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
	}
	
	private static void printArticle() {
		List<Article> result = articleDao.find(null, null);
		System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
	}
	
	private static void printComment() {
		List<Comment> result = commentDao.find(null);
		System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
	}
	
	private static void printPoem() {
		List<Poem> result = poemDao.find(null, null);
		System.out.println(result.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
	}
	
	private static void putGroup(GroupNode... groupNodes) {
		Arrays.stream(groupNodes).forEach(groupDao::update);
	}
	
	private static void putArticle(Article... articles) {
		Arrays.stream(articles).forEach(articleDao::update);
	}
	
	private static void putComment(Comment... comments) {
		Arrays.stream(comments).forEach(commentDao::update);
	}
	
	private static void printRequestInfo() {
		List<RequestInfo> requestInfos = requestInfoDao.find(null);
		System.out.println(requestInfos.stream().map(BaseEntity::toJSON).reduce(FuncUtil.joinStr("\n")).orElse(""));
	}
}

