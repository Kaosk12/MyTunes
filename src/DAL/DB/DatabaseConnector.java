package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    private SQLServerDataSource dataSource;

    public DatabaseConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MYTUNES_GRUPPE_2");
        dataSource.setUser("CSe22A_32");
        dataSource.setPassword("CSe22A_32");
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();

        try (Connection conn = dbConnector.getConnection()) {
            System.out.println("Connection works!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
