package mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MysqlUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger("MysqlUtil.class");

    //    private static final String dataBaseName = "mje";
    private String dataBaseName = "mje_datetime_test";
    private String userName = "root";
    private String password = "123456";
    private String mysqlUrl = "jdbc:mysql://localhost:3306/" + dataBaseName;

    private String tableName = null;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    private Connection connetion = null;

    public Connection getConnection() {

        try {
            this.connetion = DriverManager.getConnection(mysqlUrl, userName, password);
            LOGGER.info("Succeed to connect mysql:{}!", mysqlUrl);
        } catch (Exception e) {
            LOGGER.warn("Fail to connect mysql:{}!", mysqlUrl, e);
        }
        return this.connetion;
    }


    public void close(){
        if (this.connetion != null) {
            try {
                this.connetion.close();
            } catch (SQLException e) {
                LOGGER.warn("Fail to close connection!", e);
            }
        }
    }

    public boolean exist() {
        boolean exist = false;// default exist
        try {
            ResultSet tables = this.connetion.getMetaData().getTables(null,null, this.tableName, null);
            if (tables.next()) {
                exist = true;
            }
        } catch (SQLException e) {
           LOGGER.warn("Fail to check whether table {} exist!", this.tableName);
        }
        return exist;
    }

    public void createTable(){//mje_version_info

        String createSql = "CREATE TABLE " + this.tableName + "("
                + "mjeVersion varchar(10) not null primary key,"
                + "sizeOnDisk int(4) not null,"
                + "lastModified datetime"
                + ");";
        try {
            Statement statement = this.connetion.createStatement();
            if (statement.execute(createSql)) {
                LOGGER.info("Succeed to create table:{}!", this.tableName);
            }else {
                LOGGER.warn("Fail to create table:{}!", this.tableName);
            }
        } catch (SQLException e) {
            LOGGER.warn("Fail to create table:{}!", this.tableName, e);
        }

    }


    public void insert(String mjeVersion, int sizeOnDisk, String lastModified){

        String sql="insert ignore into " + this.tableName + " (mjeVersion, sizeOnDisk, lastModified) values ('"+ mjeVersion +"','" + sizeOnDisk +"','"+ lastModified + "');";
//        System.out.println("sql is : " + sql);
        try {
            PreparedStatement pstm = this.connetion.prepareStatement(sql);
            int row = pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("Fail to insert data to table {}!", this.tableName, e);
        }
    }


}
