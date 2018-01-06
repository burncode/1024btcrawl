package cn.sitedev.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sitedev.entity.ParentColumn;
import cn.sitedev.entity.SubColumn;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class ColumnCrawler implements CommonCrawler {
	private String parentColumnXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/li/a";
	private String columnUrlXpath = "a/@href";
	private String columnNameXpath = "a/text()";
	private String subColumnXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/ul[index]/li/a";
	private String baseUrl = "http://1024.skswk9.pw/pw/";

	@SuppressWarnings("unchecked")
	public List<String> getLinks(Page page) {
		// 定义父栏目列表
		List<ParentColumn> parentColumns = new ArrayList<ParentColumn>();
		// 定义子栏目列表
		List<SubColumn> subColumns = null;
		// 定义所有子栏目的地址
		List<String> subColumnUrlsAll = new ArrayList<String>();
		// 定义每个父栏目对应的子栏目地址
		List<String> subColumnUrlsEach = null;
		// 获取html内容
		Html html = page.getHtml();
		// 定义父栏目名
		String parentColumnName = null;
		// 获取父栏目节点所有节点
		List<Selectable> parentColumnNodes = html.xpath(parentColumnXpath)
				.nodes();
		// 遍历父栏目节点
		for (int i = 0, j = parentColumnNodes.size(); i < j; i++) {
			// 不抓取最后一个父栏目（因为没啥用）
			if (i == j - 1) {
				break;
			}
			// 获取父栏目名
			parentColumnName = parentColumnNodes.get(i).xpath(columnNameXpath)
					.get();
			// 根据父栏目索引获取对应的父栏目下的子栏目Map
			Map<String, Object> subColumnsMap = this
					.getSubColumnByParentColumnIndex(i, html);
			// 获取每个父栏目对应的子栏目实体
			subColumns = (List<SubColumn>) subColumnsMap.get("subColumns");
			// 获取每个父栏目对应的子栏目地址
			subColumnUrlsEach = (List<String>) subColumnsMap
					.get("subColumnUrls");
			// 封装父栏目
			ParentColumn parentColumn = new ParentColumn(parentColumnName,
					subColumns);
			// 将父栏目放入parentColumns中
			parentColumns.add(parentColumn);
			// 将所有子栏目的地址放入columnUrls中，以便pageProcess处理
			subColumnUrlsAll.addAll(subColumnUrlsEach);
		}
		return subColumnUrlsAll;
	}

	private Map<String, Object> getSubColumnByParentColumnIndex(int i, Html html) {
		List<SubColumn> subColumns = new ArrayList<SubColumn>();
		List<String> columnUrls = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取所有子栏目节点
		List<Selectable> subColumnsNodes = html.xpath(
				subColumnXpath.replaceAll("index", String.valueOf(i + 1)))
				.nodes();
		// 遍历子栏目节点
		for (Selectable subColumnNode : subColumnsNodes) {
			// 获取子栏目名
			String subColumnName = subColumnNode.xpath(columnNameXpath).get();
			// 获取子栏目地址
			String subColumnUrl = baseUrl
					+ subColumnNode.xpath(columnUrlXpath).get();
			// 封装子栏目
			SubColumn subColumn = new SubColumn(subColumnName, subColumnUrl);
			subColumns.add(subColumn);
			columnUrls.add(subColumnUrl);
		}
		map.put("subColumns", subColumns);
		map.put("subColumnUrls", columnUrls);
		return map;
	}

}
