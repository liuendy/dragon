package com.tianshouzhi.dragon.physical;

import java.sql.*;

/**
 * Created by tianshouzhi on 2018/1/23.
 */
public class PhysicalDataSource extends PhysicalConnectionPool {

	@Override
	public Connection getConnection() throws SQLException {
		return super.borrowConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException("borrowConnection(String username, String password) is not Support!!!");
	}
}