package cn.sitedev.crawler;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

import cn.sitedev.entity.PicText;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

public class PicTextCrawler extends HtmlNode implements CommonCrawler {
	private String lastPageNoXpath = "//table[@class='i_table'][2]/tbody/tr/td/center/a[last()]";
	private String lastPageNoQuery = "table[class='i_table']:eq(1) tbody tr td center a:eq(-1)";
	private String curPageNoXpath = "//table[@class='i_table'][2]/tbody/tr/td/center/b/text()";
	private String picTextNodeXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/li";
	private String picTextSourceUrlXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/li[index]/a";
	private String textOfTagAXpath = "a/text()";
	private String hrefOfTagAXpath = "a/@href";
	private String picUrlXpath = "//table[@class='i_table'][2]/tbody/tr/td/table/tbody/tr/td[@class='tpc_content']/img/@src";
	private String textUrlXpath = "//table[@class='i_table'][2]/tbody/tr/td/table/tbody/tr/td[@class='tpc_content']/b/text()";
	private String baseUrlReg = "http://1024\\.skswk9\\.pw/pw/simple/index\\.php\\?";
	private String picTextUrlReg = baseUrlReg + "t\\d+\\.html";

	public List<PicText> getLinks(Page page) {
		Selectable selectable = page.getHtml();
		List<PicText> picTexts = new ArrayList<PicText>();
		PicText picText = null;
		int pageNum = selectable.xpath(picTextNodeXpath).nodes().size();
		int lastPageNo = Integer.parseInt(selectable.$(lastPageNoQuery)
				.xpath(hrefOfTagAXpath).get().split("_")[1].split("\\.")[0]);
		for (int i = 1; i < lastPageNo; i++) {
			for (int j = 1; j < pageNum; j++) {
				String sourceUrl = selectable
						.xpath(picTextSourceUrlXpath.replaceAll("index",
								String.valueOf(i + 1))).xpath(hrefOfTagAXpath)
						.get();
				String picTextName = selectable.xpath(picTextSourceUrlXpath)
						.xpath(textOfTagAXpath).get();
				List<String> picList = selectable.xpath(picUrlXpath).all();
				String text = selectable.xpath(textUrlXpath).get();
				picText = new PicText(picTextName, sourceUrl, picList, text);
				picTexts.add(picText);
			}

		}
		return picTexts;
	}

}
