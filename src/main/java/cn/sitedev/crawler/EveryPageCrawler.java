package cn.sitedev.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import cn.sitedev.util.NumberUtil;

public class EveryPageCrawler implements CommonCrawler {
	private String lastPageNoQuery = "//table[@class='i_table'][2]/tbody/tr/td/center/text()";

	public List<String> getLinks(Page page) throws IOException {
		List<String> everyPageUrls = null;
		List<String> detailListUrls = null;
		List<String> list = new ArrayList<String>();
		// 如果是子栏目首页的地址，就获取子栏目其他页的地址以及抓取首页的列表详情地址
		everyPageUrls = this.getSubColumnOtherUrls(page);
		DetailListCrawler detailListCrawler = new DetailListCrawler();
		detailListUrls = detailListCrawler.getLinks(page);
		list.addAll(everyPageUrls);
		list.addAll(detailListUrls);
		page.putField("everyPageUrls", everyPageUrls);
		page.putField("detailListUrls", detailListUrls);
		return list;
	}

	/**
	 * 抓取子栏目其他页面的地址
	 * 
	 * @param page
	 * @return
	 */
	private List<String> getSubColumnOtherUrls(Page page) {
		String everyPageUrl = null;
		List<String> everyPageUrls = new ArrayList<String>();

		String lastPageNoStrFromPage = page.getHtml().xpath(lastPageNoQuery)
				.get();
		int lastPageNo = NumberUtil.getNumber(lastPageNoStrFromPage);
		for (int i = 2; i <= lastPageNo; i++) {
			// 子栏目其他页面地址=首页地址_页码.html
			everyPageUrl = page.getUrl().get();
			// 拼接成指定页码的页面地址
			everyPageUrl = everyPageUrl.replace(".html", "_" + i + ".html");
			everyPageUrls.add(everyPageUrl);
		}
		return everyPageUrls;
	}

}
