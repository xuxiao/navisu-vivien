/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bzh.terrevirtuelle.navisu.database.impl;

import bzh.terrevirtuelle.navisu.database.Database;
import bzh.terrevirtuelle.navisu.database.DatabaseServices;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.capcaval.c3.component.ComponentState;

/**
 * @date 13 mars 2015
 * @author Serge Morvan
 */
public class DatabaseImpl
        implements Database, DatabaseServices, ComponentState {

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection;

    @Override
    public void componentInitiated() {
    }

    @Override
    public void componentStarted() {
    }

    @Override
    public void componentStopped() {
    }

    @Override
    public Statement connect(String dbName, String hostName, String protocol, String port, String driverName, String userName, String passwd) {
        try {
            Class.forName(driverName);
            String url = protocol + hostName + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, userName, passwd);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }

    @Override
    public void execute(String statement) {
        try {
            this.statement.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public PreparedStatement prepare(String statement) {
        try {
            preparedStatement = connection.prepareStatement(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preparedStatement;
    }

    @Override
    public void createIndex(String indexName, String tableName, String columnName) {
        try {
            statement.execute("CREATE INDEX " + indexName + " ON " + tableName + " USING GIST (" + columnName + ")");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void drop(String tableName) {
        try {
            statement.execute("DROP TABLE " + tableName);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}