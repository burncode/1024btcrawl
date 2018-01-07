package cn.sitedev.pageprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.sitedev.crawler.ColumnCrawler;
import cn.sitedev.crawler.DetailCrawler;
import cn.sitedev.crawler.DetailListCrawler;
import cn.sitedev.crawler.EveryPageCrawler;
import cn.sitedev.util.PropUtil;

import com.alibaba.fastjson.JSONArray;

public class MyPageProcess implements PageProcessor {
	private final static String indexUrl = "http://1024.skswk9.pw/pw/simple";
	private String subColumnIndexUrlReg;
	private String subColumnOtherUrlReg;
	private String detailUrlReg;
	private Site site = Site.me().setRetryTimes(5).setTimeOut(3000).setSleepTime(3000);
	@Resource
	private ColumnCrawler columnCrawler;

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		List<String> list = new ArrayList<String>();
		String pageUrl = page.getUrl().get();
		try {
			subColumnIndexUrlReg = PropUtil.getProp("subColumnIndexUrlReg");
			subColumnOtherUrlReg = PropUtil.getProp("subColumnOtherUrlReg");
			detailUrlReg = PropUtil.getProp("detailUrlReg");

			// 如果是首页地址，就抓取所有子栏目的链接地址
			if (indexUrl.equals(pageUrl)) {
				ColumnCrawler columnCrawler = new ColumnCrawler();
				// 获取抓取的链接
				List<String> subColumnUrls = columnCrawler.getLinks(page);
				System.out.println(subColumnUrls);
				list.addAll(subColumnUrls);
				JSONArray jsonArray = (JSONArray) JSONArray
						.toJSON(subColumnUrls);
				System.out.println(jsonArray);
			}
			// 如果是子栏目的首页链接地址，就抓取子栏目中列表中各个元素对应的详情的链接地址以及子栏目其他页的的链接地址
			if (page.getUrl().regex(subColumnIndexUrlReg).match()) {
				EveryPageCrawler everyPageCrawler = new EveryPageCrawler();
				List<String> everyPages = everyPageCrawler.getLinks(page);
				list.addAll(everyPages);
				JSONArray jsonArray = (JSONArray) JSONArray.toJSON(everyPages);
				System.out.println(jsonArray);
			}
			// 如果是子栏目的其他页面地址，就抓取详情列表
			if (page.getUrl().regex(subColumnOtherUrlReg).match()) {
				DetailListCrawler detailListCrawler = new DetailListCrawler();
				List<String> detailListUrls = detailListCrawler.getLinks(page);
				list.addAll(detailListUrls);
				JSONArray jsonArray = (JSONArray) JSONArray
						.toJSON(detailListUrls);
				System.out.println(jsonArray);
			}
			// 如果是详情的地址，就抓取其中的内容
			if (page.getUrl().regex(detailUrlReg).match()) {
				DetailCrawler detailCrawler = new DetailCrawler();
				detailCrawler.getLinks(page);
			}
//			if (list.size() == 0) {
//				// 本页将会跳过，不被Pipeline处理
//				page.setSkip(true);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		page.addTargetRequests(list);

	}

	public static void main(String[] args) {
		Spider.create(new MyPageProcess())
				// 从网站首页开始抓
				.addUrl(indexUrl)
				.addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
				// 开启5个线程抓取
				.thread(5)
				// 启动爬虫
				.run();
	}

}
