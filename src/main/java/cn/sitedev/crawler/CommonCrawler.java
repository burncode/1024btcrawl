package cn.sitedev.crawler;

import java.util.List;

import us.codecraft.webmagic.Page;

public interface CommonCrawler {
	public List<?> getLinks(Page page);
}
