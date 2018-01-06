package cn.sitedev.entity;

import java.util.List;

public class PicText {
	// 类型
	private String type;
	// 名称
	private String name;
	// 源地址
	private String sourceUrl;
	// 图片地址
	private List<String> picList;
	// 文本内容
	private String text;

	public PicText(String picTextName, String sourceUrl, List<String> picList,
			String text) {
		this.name = picTextName;
		this.sourceUrl = sourceUrl;
		this.picList = picList;
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
