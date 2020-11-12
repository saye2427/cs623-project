package team4project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLT4ACID {

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		
		//Load PostgreSQL driver
		Class.forName("org.postgresql.Driver");
		
		//Connect to the default database with credentials
		//Enter your own machine's port #/dbName for postgre after localhost
		String url = "jdbc:postgresql://localhost:5432/dbname";
		//Enter your own machine's root here
		String root = "postgres";
		//Enter your own postgre password here
		String password = "password";
				
		Connection connect = DriverManager.getConnection(url, root, password);
		
		//For Atomicity
		connect.setAutoCommit(false); //this ensures that the "all or nothing" aspect of atomicity is upheld as queries aren't committed as soon as they are sent to the database
		
		//For Isolation
		connect.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); //make transactions serializable
		
		Statement query = null;
		try {
			
			//Create statement object for sending SQL statements/queries to the database
			query = connect.createStatement();
			
			//Transaction 4: Depot d1 changes its name to dd1 in Depot and Stock
			//Execute #dep change for d1, where in order to remain ACID compliant,
			//the changes must be made in both Depot and Stock at once, or neither at all (Atomicity)
			
			//This method uses CASCADE
//			//We did NOT use ON UPDATE CASCADE when creating the tables, because then we could not show ATOMICITY
//			//However, a CASCADE is required to change depId since it is a foreign key constraint for Stock
//			//Thus, to demonstrate Atomicity here, we must update the foreign key constraint that already exists, now with a CASCADE (i.e. DROP it and ADD it again)
//			//And then simply UPDATE Depot (and show that it also automatically updates Stock due to the dependency and the fact that all these statements will be executed at once)
//			query.execute("ALTER TABLE Stock DROP CONSTRAINT fk_stock_depot");
//			query.execute("ALTER TABLE Stock ADD CONSTRAINT fk_stock_depot FOREIGN KEY(depId) REFERENCES Depot(depId) ON UPDATE CASCADE");
//			query.executeUpdate("UPDATE Depot SET depId = 'dd1' WHERE depId = 'd1'");
//			//query.executeUpdate("UPDATE Stock SET depId = 'dd1' WHERE depId = 'd1'");
			
			//Method not using CASCADE, but mimicking it
			//We can just drop FKs, make changes to BOTH tables, and then reinstate the FK constraints
			query.execute("ALTER TABLE Stock DROP CONSTRAINT fk_stock_depot");
			query.executeUpdate("UPDATE Depot SET depId = 'dd1' WHERE depId = 'd1'");
			query.executeUpdate("UPDATE Stock SET depId = 'dd1' WHERE depId = 'd1'");
			query.execute("ALTER TABLE Stock ADD CONSTRAINT fk_stock_depot FOREIGN KEY(depId) REFERENCES Depot(depId)");
			
			//To show/print the changes, we need to use ResultSet
			ResultSet depot = query.executeQuery("SELECT * FROM Depot");
			ResultSet stock = query.executeQuery("SELECT * FROM Stock");
			
			//Print data in depot table (should show d1 as dd1 now)
			while(depot.next()) {
				System.out.println("depId: " + depot.getString("depId") + " addr: " + depot.getString("addr") + " volume: " + depot.getInt("volume"));
			}
			
			//Print data in stock table (should show d1 as dd1 now)
			while(stock.next()) {
				System.out.println("prodId: " + stock.getString("prodId") + " depId: " + stock.getString("depId") + " quantity: " + stock.getInt("quantity"));
			}
			
		} catch (SQLException e){
			System.out.println(e); //for debugging purposes, let us print the actual Exception
			
			//To ensure atomicity
			connect.rollback(); //if an Exception is thrown, rollback undoes any changes made in the current transaction
			query.close();
			connect.close();
			return;
		}
		
		//Since autocommit is disabled, we manually commit changes since the last rollback
		//and release any database locks held by the Connection object
		connect.commit();
		query.close();
		connect.close();

	}

}
