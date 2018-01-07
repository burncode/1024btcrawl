package cn.sitedev.crawler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import us.codecraft.webmagic.Page;
import cn.sitedev.util.PropUtil;

public class DetailCrawler implements CommonCrawler {
	private String xpsdSubColumns = null;
	private String wpzySubColumns = null;
	private String grzqSubColumns = null;
	private String twxsSubColumns = null;
	private String subColumnNameXpath = "//table[@class='i_table'][1]/tbody/tr/td/table/tbody/tr/td[1]/b/a[2]/text()";
	private String detailNameXpath = "//table[@class='i_table'][1]/tbody/tr/td/table/tbody/tr/td[1]/b/a[3]/text()";

	public List<?> getLinks(Page page) throws IOException {
		// 获取子栏目标题
		String subColumnName = page.getHtml().xpath(subColumnNameXpath).get();
		List<String> urls = this.crawlBySubColumnName(page, subColumnName);
		return urls;
	}

	private List<String> crawlBySubColumnName(Page page, String subColumnName)
			throws IOException {
		// 获取详情名
		String detailName = page.getHtml().xpath(detailNameXpath).get();
		List<String> xpsdList = Arrays.asList(PropUtil
				.getProp("xpsdSubColumns"));
		List<String> wpzyList = Arrays.asList(PropUtil
				.getProp("wpzySubColumns"));
		List<String> grzqList = Arrays.asList(PropUtil
				.getProp("grzqSubColumns"));
		List<String> twxsList = Arrays.asList(PropUtil
				.getProp("twxsSubColumns"));
		if (twxsList.contains(subColumnName)) {
			PicTextCrawler picTextCrawler = new PicTextCrawler();
			picTextCrawler.getLinks(page);
		}
		return null;

	}
}
