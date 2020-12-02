package cn.edu.xmu.oomall.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
	private Integer page;
	private Integer pageSize;

	private Integer pages;
	private Long total;

	public PageInfo(Integer page, Integer pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	public void calAndSetPagesAndTotal(long totalElements, Integer pages) {
		this.total = totalElements;
		this.pages = pages;
	}
}