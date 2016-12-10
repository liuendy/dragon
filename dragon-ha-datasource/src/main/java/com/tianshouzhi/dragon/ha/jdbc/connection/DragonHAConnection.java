package com.tianshouzhi.dragon.ha.jdbc.connection;

import com.tianshouzhi.dragon.ha.jdbc.statement.DragonHAPrepareStatement;
import com.tianshouzhi.dragon.ha.jdbc.statement.DragonHAStatement;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by TIANSHOUZHI336 on 2016/12/3.
 */
public class DragonHAConnection extends HAConnectionAdapter implements Connection{
    List<Statement> statementList=new CopyOnWriteArrayList<Statement>();
    public DragonHAConnection(HAConnectionManager HAConnectionManager) throws SQLException {
        this(null,null, HAConnectionManager);
    }

    public DragonHAConnection(String userName, String password, HAConnectionManager HAConnectionManager) throws SQLException {
        super(userName, password, HAConnectionManager);
    }

    @Override
    public Statement createStatement() throws SQLException {
        DragonHAStatement dragonHAStatement = new DragonHAStatement(this);
        statementList.add(dragonHAStatement);
        return dragonHAStatement;
    }
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        DragonHAStatement dragonHAStatement = new DragonHAStatement(resultSetType, resultSetConcurrency, this);
        statementList.add(dragonHAStatement);
        return dragonHAStatement;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        DragonHAStatement dragonHAStatement = new DragonHAStatement(resultSetType, resultSetConcurrency, resultSetHoldability, this);
        statementList.add(dragonHAStatement);
        return dragonHAStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, resultSetType, resultSetConcurrency, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, autoGeneratedKeys, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, columnIndexes, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        DragonHAPrepareStatement dragonHAPrepareStatement = new DragonHAPrepareStatement(sql, columnNames, this);
        statementList.add(dragonHAPrepareStatement);
        return dragonHAPrepareStatement;
    }

    /**
     * 因为不知道存储过程中到底执行了什么，所以：
     * 1、CallableStatement总是应该获取写连接
     * 2、CallableStatement不重试，不需要建立一个类似的DragonHACallableStatement
     * 3、Hint的问题
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        if(realConnection==null||realConnection.isReadOnly()){
            buildNewWriteConnection();
        }
        return realConnection.prepareCall(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if(realConnection==null||realConnection.isReadOnly()){
            buildNewWriteConnection();
        }
        return realConnection.prepareCall(sql,resultSetType,resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if(realConnection==null||realConnection.isReadOnly()){
            buildNewWriteConnection();
        }
        return realConnection.prepareCall(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
    }

    /**
     * 针对关闭connection是否会自动关闭Statement和ResultSet的问题，以及Statement和ResultSet所占用资源是否会自动释放问题，JDBC处理规范或JDK规范中做了如下描述：
     * 1、Connection关闭不一定会导致Statement关闭。
     * 2、Statement关闭会导致ResultSet关闭；
     * 3、如果直接关闭了Connection，Statemnt会有垃圾回收机制自动关闭
     *  由于垃圾回收的线程级别是最低的，为了充分利用数据库资源，有必要显式关闭它们，最优经验是按照ResultSet，Statement，Connection的顺序执行close
     *   为了避免由于java代码有问题导致内存泄露，需要在rs.close()和stmt.close()后面一定要加上rs = null和stmt = null；
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        if (realConnection != null) {
            realConnection.close();
            realConnection=null;
        }
        for (Statement statement : statementList) {
            if(!statement.isClosed()){
                statement.close();
            }
            statement=null;
        }
        isClosed = true;
    }
}