package com.tianshouzhi.dragon.common.initailzer.impl;

import com.tianshouzhi.dragon.common.initailzer.AbstractDataSourceAdapter;

/**
 * Created by TIANSHOUZHI336 on 2017/3/21.
 */
public class ProxoolDataSourceAdapter extends AbstractDataSourceAdapter {
	@Override
	public String getClassName() {
		return "org.logicalcobwebs.proxool.ProxoolDataSource";
	}
}
