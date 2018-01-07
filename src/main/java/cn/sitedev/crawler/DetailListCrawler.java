package cn.sitedev.crawler;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.Page;
import cn.sitedev.util.PropUtil;

public class DetailListCrawler implements CommonCrawler {
	private String hrefOfTagAXpath = null;
	private String indexUrl = null;
	private String detailListUrlXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/li/a";

	public List<String> getLinks(Page page) throws IOException {
		indexUrl = PropUtil.getProp("indexUrl");
		hrefOfTagAXpath = PropUtil.getProp("hrefOfTagAXpath");
		List<String> detailListUrls = page.getHtml().xpath(detailListUrlXpath)
				.xpath(hrefOfTagAXpath).all();
		for (int i = 0, j = detailListUrls.size(); i < j; i++) {
			String detailUrl = detailListUrls.get(i);
			// 给地址加上域名
			detailListUrls.set(i, indexUrl + detailUrl);
		}
		page.putField("detailListUrls", detailListUrls);
		return detailListUrls;
	}

}
