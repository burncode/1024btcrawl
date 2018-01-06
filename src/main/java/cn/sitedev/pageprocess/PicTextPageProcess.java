package cn.sitedev.pageprocess;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.sitedev.crawler.ColumnCrawler;

import com.alibaba.fastjson.JSONArray;

public class PicTextPageProcess implements PageProcessor {
	private final static String indexUrl = "http://1024.skswk9.pw/pw/simple";
	private String baseUrlReg;
	private String subColumnUrlReg;
	private String detailUrlReg;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		List<String> list = new ArrayList<String>();
		String pageUrl = page.getUrl().get();
		// 如果是首页地址，就抓取所有子栏目的链接地址
		if (indexUrl.equals(pageUrl)) {
			ColumnCrawler columnCrawler = new ColumnCrawler();
			// 获取抓取的链接
			List<String> subColumnUrls = columnCrawler.getLinks(page);
			System.out.println(subColumnUrls);
			list.addAll(subColumnUrls);
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(subColumnUrls);
			System.out.println(jsonArray);
		}
		// 如果是子栏目的链接地址，就抓取子栏目中列表中各个元素对应的详情的链接地址
		if (page.getUrl().regex(subColumnUrlReg).match()) {

		}
		// 如果是详情的地址，就抓取其中的内容，不需要返回其他的地址
		if (page.getUrl().regex(detailUrlReg).match()) {

		}
		if (list.size() == 0) {
			// 本页将会跳过，不被Pipeline处理
			page.setSkip(true);
		}
		page.addTargetRequests(list);

	}

	public static void main(String[] args) {
		Spider.create(new PicTextPageProcess())
				// 从"https://github.com/code4craft"开始抓
				.addUrl(indexUrl)
				.addPipeline(new FilePipeline("D:\\webmagic\\"))
				// 开启5个线程抓取
				.thread(5)
				// 启动爬虫
				.run();
	}

}
