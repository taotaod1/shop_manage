package com.bt.vo;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("rawtypes")
public class CatVo {
	private Integer value = null;
	private String label = null;
	private List<CatVo> children = null;
}
