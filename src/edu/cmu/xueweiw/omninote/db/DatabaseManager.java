/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.rangesoftware.mengw.omninote.model.Model;
import net.rangesoftware.mengw.omninote.model.Note;
import net.rangesoftware.mengw.omninote.model.User;

/**
 * 
 *
 */
public class DatabaseManager {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/omninote";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	private Connection connection;
	private static DatabaseManager instance;

	private DatabaseManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static synchronized DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	public DatabaseManager open() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean save(Model entity) {
		try {
			Statement statement = connection.createStatement();
			String savesql = SQLUtil.getInsertSQL(entity);
			statement.executeUpdate(savesql, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				entity.setId(resultSet.getInt(1));
			}
			close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		}

	}

	public boolean update(Model entity) {
		try {
			Statement statement = connection.createStatement();
			String updatesql = SQLUtil.getUpdateSQL(entity);
			statement.execute(updatesql);
			close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		}

	}

	public boolean delete(Model entity) {
		try {
			Statement statement = connection.createStatement();
			String updatesql = SQLUtil.getDeleteSQL(entity);
			statement.execute(updatesql);
			close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		}

		//
	}

	public Model findById(Integer id, Class<? extends Model> cls) {
		List<Model> searchResult = findByFieldName(cls, "id", id);
		if (searchResult != null && searchResult.size() > 0) {
			return searchResult.get(0);
		} else {
			return null;
		}

	}

	public List<Model> findByFieldName(Class<? extends Model> cls, String fieldName, Object value) {
		// List<Model> result = new ArrayList<Model>();
		try {
			Statement statement = connection.createStatement();
			String updatesql = SQLUtil.getSelectSQL(cls, fieldName, value);
			ResultSet resultSet = statement.executeQuery(updatesql);
			List<Model> results = SQLUtil.ResultSetToList(resultSet, cls);
			close();

			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}

		return null;
	}

	public List<Model> findAll(Class<? extends Model> cls) {
		// List<Model> result = new ArrayList<Model>();
		try {
			Statement statement = connection.createStatement();
			String updatesql = SQLUtil.getSelectSQL(cls);
			ResultSet resultSet = statement.executeQuery(updatesql);
			List<Model> results = SQLUtil.ResultSetToList(resultSet, cls);
			close();

			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}

		return null;
	}

	public List<Model> findNoteByRange(double d, double f, int radius_km) {
		// TODO Auto-generated method stub
		try {
			Statement statement = connection.createStatement();
			String updatesql = SQLUtil.getNoteRangeSQL(Note.class, d, f, radius_km);
			ResultSet resultSet = statement.executeQuery(updatesql);
			List<Model> results = SQLUtil.ResultSetToList(resultSet, Note.class);
			close();

			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}
		return null;

	}

}
