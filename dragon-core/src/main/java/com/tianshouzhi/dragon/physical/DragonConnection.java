package com.tianshouzhi.dragon.physical;

import com.tianshouzhi.dragon.common.jdbc.WrapperAdapter;

import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by tianshouzhi on 2018/1/26.
 */
public class DragonConnection extends WrapperAdapter implements Connection,PooledConnection {
	protected Connection delegate;

	private long lastActiveTime = System.currentTimeMillis();

	private DragonDataSource dataSource;

	public DragonConnection(Connection delegate, DragonDataSource dataSource ) {
		this.delegate = delegate;
		this.dataSource = dataSource;
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new DragonStatement(delegate.createStatement(), this);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new DragonPrepareStatement(delegate.prepareStatement(sql), this);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return new DragonCallableStatement(delegate.prepareCall(sql), this);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return delegate.nativeSQL(sql);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		delegate.setAutoCommit(autoCommit);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return delegate.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		delegate.commit();
	}

	@Override
	public void rollback() throws SQLException {
		delegate.rollback();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return delegate;
	}

	@Override
	public void close() throws SQLException {
		if (!isClosed()) {
			dataSource.returnConnection(this);
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		return delegate.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return delegate.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		delegate.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return delegate.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		delegate.setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException {
		return delegate.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		delegate.setTransactionIsolation(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return delegate.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return delegate.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		delegate.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return delegate.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
	      throws SQLException {
		return delegate.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return delegate.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		delegate.setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		delegate.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		return delegate.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return delegate.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return delegate.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		delegate.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		delegate.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
	      throws SQLException {
		return delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
	      int resultSetHoldability) throws SQLException {
		return delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
	      int resultSetHoldability) throws SQLException {
		return delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return delegate.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return delegate.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return delegate.prepareStatement(sql, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		return delegate.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		return delegate.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return delegate.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return delegate.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return delegate.isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		delegate.setClientInfo(name, value);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		delegate.setClientInfo(properties);
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return delegate.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return delegate.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return delegate.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return delegate.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		delegate.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return delegate.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		delegate.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		delegate.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return delegate.getNetworkTimeout();
	}

	void updateLastActiveTime() {
		this.lastActiveTime = System.currentTimeMillis();
	}
	
	long getLastActiveTime() {
		return this.lastActiveTime;
	}
	

	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {

	}

	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {

	}

	@Override
	public void addStatementEventListener(StatementEventListener listener) {

	}

	@Override
	public void removeStatementEventListener(StatementEventListener listener) {

	}
}
