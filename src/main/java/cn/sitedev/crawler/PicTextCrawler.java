package cn.sitedev.crawler;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.HtmlNode;
import cn.sitedev.entity.PicText;

public class PicTextCrawler extends HtmlNode implements CommonCrawler {

	private String picUrlXpath = "//td[@class='tpc_content']/img/@src";
	private String textXpath = "//td[@class='tpc_content']/text()";
	private String picTextNameXpath = "//table[@class='i_table'][1]/tbody/tr/td/table/tbody/tr/td[1]/b/a[3]/text()";
	private String picTextTypeXpath = "//table[@class='i_table'][1]/tbody/tr/td/table/tbody/tr/td[1]/b/a[2]/text()";

	public List<PicText> getLinks(Page page) throws IOException {
		// 获取图文名
		String detailName = page.getHtml().xpath(picTextNameXpath).get();
		// 获取图文类型（即所属子栏目名）
		String type = page.getHtml().xpath(picTextTypeXpath).get();
		// 获取图片列表
		List<String> picList = page.getHtml().xpath(picUrlXpath).all();
		// 获取文字内容
		String text = page.getHtml().xpath(textXpath).get();
		PicText picText = new PicText(detailName, type, picList, text);
		page.putField("picText", picText);
		return null;

	}

}
