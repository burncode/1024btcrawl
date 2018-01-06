package cn.sitedev.entity;

public class SubColumn extends ColumnBase {
	/**
	 * 
	 * @param subColumnName
	 * @param subColumnUrl
	 */
	public SubColumn(String subColumnName, String subColumnUrl) {
		this.name = subColumnName;
		this.url = subColumnUrl;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "SubColumn [url=" + url + ", name=" + name + "]";
	}

}
