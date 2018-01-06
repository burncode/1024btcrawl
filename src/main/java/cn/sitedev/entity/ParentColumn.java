package cn.sitedev.entity;

import java.util.List;

public class ParentColumn extends ColumnBase {
	private List<SubColumn> subColumns;

	public ParentColumn(String parentColumnName, List<SubColumn> subColumns) {
		this.name = parentColumnName;
		this.subColumns = subColumns;
	}

	public List<SubColumn> getSubColumns() {
		return subColumns;
	}

	public void setSubColumns(List<SubColumn> subColumns) {
		this.subColumns = subColumns;
	}

	@Override
	public String toString() {
		return "ParentColumn [subColumns=" + subColumns + ", name=" + name
				+ "]";
	}

}
