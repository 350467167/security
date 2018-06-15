package com.ltkj.template.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageInfo extends UserBackup {
	private int pageNum = 1;
	private int pageSize = 20;
	private int total;

	public int getBeginIndex() {
		return (pageNum - 1) * pageSize;
	}
}
