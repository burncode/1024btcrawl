package cn.sitedev.crawler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import cn.sitedev.entity.ParentColumn;
import cn.sitedev.entity.SubColumn;
import cn.sitedev.util.PropUtil;

/**
 * 栏目地址抓取
 * 
 * @author QChen
 * 
 */
public class ColumnCrawler implements CommonCrawler {
	private String hrefOfTagAXpath = null;
	private String textOfTagAXpath = null;
	private String indexUrl = null;
	private String parentColumnXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/li/a";
	private String subColumnXpath = "//table[@class='i_table'][2]/tbody/tr/td/ul/ul[index]/li/a";

	/**
	 * 取得抓取地址
	 * 
	 * @param page
	 *            page
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getLinks(Page page) throws IOException {
		hrefOfTagAXpath = PropUtil.getProp("hrefOfTagAXpath");
		textOfTagAXpath = PropUtil.getProp("textOfTagAXpath");
		indexUrl = PropUtil.getProp("indexUrl");
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

			if (i == 0 || i == 1 || i == 2) {
				continue;
			}
			// 不抓取最后一个父栏目（因为没啥用）
			if (i == j - 1) {
				break;
			}
			// 获取父栏目名
			parentColumnName = parentColumnNodes.get(i).xpath(textOfTagAXpath)
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
			page.putField("parentColumns", parentColumns);
			// 将所有子栏目的地址放入columnUrls中，以便pageProcess处理
			subColumnUrlsAll.addAll(subColumnUrlsEach);
		}
		return subColumnUrlsAll;
	}

	/**
	 * 根据父栏目索引获取对应子栏目
	 * 
	 * @param index
	 *            索引
	 * @param html
	 *            html
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> getSubColumnByParentColumnIndex(int index,
			Html html) throws IOException {
		List<SubColumn> subColumns = new ArrayList<SubColumn>();
		List<String> columnUrls = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取所有子栏目节点
		List<Selectable> subColumnsNodes = html.xpath(
				subColumnXpath.replaceAll("index", String.valueOf(index + 1)))
				.nodes();
		List<String> twxsSubColumns = Arrays.asList(PropUtil.getProp("twxsSubColumns").split(","));
		// 遍历子栏目节点
		for (Selectable subColumnNode : subColumnsNodes) {
			// 获取子栏目名
			String subColumnName = subColumnNode.xpath(textOfTagAXpath).get();

			if (!twxsSubColumns.contains(subColumnName)) {
				continue;
			}
			// 获取子栏目地址
			String subColumnUrl = indexUrl
					+ subColumnNode.xpath(hrefOfTagAXpath).get();
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
