package net.dirtcraft.plugins.dirtrewards.database;

import com.zaxxer.hikari.HikariDataSource;
import net.dirtcraft.plugins.dirtrewards.DirtRewards;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Database {

	private static HikariDataSource ds;

	private static void createDataSource() {
		try {
			ds = new HikariDataSource();
			ds.setDriverClassName("org.h2.Driver");
			ds.setJdbcUrl("jdbc:h2:" + DirtRewards.getPlugin().getDataFolder().getAbsolutePath() + "/data/dirtrewards");
			ds.setConnectionTimeout(1000);
			ds.setLoginTimeout(5);
			ds.setAutoCommit(true);
			Utilities.log(Level.INFO, "Creating new Data Source ...");
		} catch (SQLException e) {
			if (Utilities.config.general.debug) {
				Utilities.log(Level.SEVERE, "Could not create DataSource! Shutting down plugin ...");
				e.printStackTrace();
			}
			Utilities.disablePlugin();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (ds == null) {
			createDataSource();
		}
		return ds.getConnection();
	}

	public static void initialiseDatabase() {
		InputStream inputStream = DirtRewards.getPlugin().getResource("dbsetup.sql");
		String setup = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

		String[] queries = setup.split(";");
		for (String query : queries) {
			try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
				statement.execute();
			} catch (SQLException e) {
				if (Utilities.config.general.debug) {
					Utilities.log(Level.SEVERE, "Could not execute initialisation queries! Shutting down plugin ...");
					e.printStackTrace();
				}
				Utilities.disablePlugin();
				return;
			}
		}

		Utilities.log(Level.INFO, "Database initialised!");
	}

	public static void closeDatabase() {
		if (ds != null) {
			ds.close();
		}
	}
}
