/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.db;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.cmu.xueweiw.omninote.db.Column.DataType;
import net.rangesoftware.mengw.omninote.model.Model;
import net.rangesoftware.mengw.omninote.model.Note;

/**
 * 
 *
 */
public class SQLUtil {

	public static String getCreateTableSQL(Class<? extends Model> modelClass) {
		Model m = null;
		try {
			System.out.println(modelClass.newInstance());
			m = modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder sb = new StringBuilder(100);
		sb.append("CREATE TABLE IF NOT EXISTS ");
		sb.append(m.tableName()).append("(").append(m.idColumnName())
				.append(" INTEGER PRIMARY KEY AUTO_INCREMENT");
		for (Field f : m.getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(Column.class)) {
				Column c = f.getAnnotation(Column.class);
				if (!c.pk()) {
					sb.append(",").append(c.name()).append(" ")
							.append(c.type());
				}
			}
		}
		sb.append(");");
		return sb.toString();
	}

	public static String getDropTableSQL(Class<? extends Model> modelClass) {
		Model m = null;
		try {
			m = modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		StringBuilder sb = new StringBuilder(35);
		sb.append("DROP TABLE IF EXISTS ").append(m.tableName()).append(";");
		return sb.toString();
	}

	public static List<Model> ResultSetToList(ResultSet resultSet,
			Class<? extends Model> cls) {
		if (resultSet == null) {
			return null;
		}
		List<Model> results = new ArrayList<Model>();
		try {
			while (resultSet.next()) {
				Model entity = null;
				entity = cls.newInstance();
				entity.setFieldsByResultSet(resultSet);
				results.add(entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return results;
	}

	public static String getInsertSQL(Model entity) {
		Map<String, String> keyValueMap = entity.toKeyValueMap();
		String savequery = "INSERT into " + entity.tableName();
		String field = "(";
		String value = " values(";
		for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
			field += entry.getKey() + ",";
			value += entry.getValue() + ",";
		}

		return savequery + field.substring(0, field.length() - 1) + ")"
				+ value.substring(0, value.length() - 1) + ")";
	}

	public static String getUpdateSQL(Model entity) {
		Map<String, String> keyValueMap = entity.toKeyValueMap();
		String updatequery = "UPDATE " + entity.tableName() + " SET ";
		for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
			updatequery += entry.getKey() + "=" + entry.getValue() + ", ";
		}

		return updatequery.substring(0, updatequery.length() - 2)
				+ " WHERE _id = " + entity.getId();
	}

	public static String getDeleteSQL(Model entity) {

		return "DELETE FROM " + entity.tableName() + " WHERE "
				+ entity.idColumnName() + " = " + entity.getId();
	}

	public static String getSelectSQL(Class<? extends Model> cls,
			String fieldName, Object value) {
		Model entity;
		String args = value.toString();
		String selectSQLString = "";
		try {
			entity = cls.newInstance();
			String where = entity.columnName(fieldName) + " = ";
			if (!fieldName.equals("id")) {
				DataType type = cls.getDeclaredField(fieldName)
						.getAnnotation(Column.class).type();
				if (type == DataType.TEXT) {
					args = "'" + value + "'";
				}
			}
			selectSQLString = "SELECT * FROM " + entity.tableName() + " WHERE " + where
					+ args;
			if (entity instanceof Note) {
				selectSQLString += " order by date desc";
			}
			System.out.println(selectSQLString);
			return selectSQLString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getSelectSQL(Class<? extends Model> cls) {
		Model entity;
		try {
			entity = cls.newInstance();
			return "SELECT * FROM " + entity.tableName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getNoteRangeSQL(Class<Note> cls, double d,
			double f, int radius_km) {
		// TODO Auto-generated method stub

		return "SELECT *, ( 3959 * acos( cos( radians(" + f
				+ ") ) * cos( radians( note.latitude ) )"
				+ "* cos( radians(note.longitude) - radians(" + d
				+ ")) + sin(radians(" + f
				+ ")) * sin( radians(note.latitude)))) AS distance "
				+ "FROM note " + " HAVING distance < " + radius_km;
	}
}
