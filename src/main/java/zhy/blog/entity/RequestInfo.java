package zhy.blog.entity;

public class RequestInfo extends BaseEntity {
	private int id;
	private String url;
	private String ip;
	
	public String getUrl() {
		return url;
	}
	
	public RequestInfo setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public String getIp() {
		return ip;
	}
	
	public RequestInfo setIp(String ip) {
		this.ip = ip;
		return this;
	}
	
	@Override
	public BaseEntity assertValid() {
		return this;
	}
}
