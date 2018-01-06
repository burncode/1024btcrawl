package cn.sitedev.entity;

public class ColumnBase {
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ColumnBase [name=" + name + "]";
	}

}
