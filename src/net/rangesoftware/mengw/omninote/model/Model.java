package net.rangesoftware.mengw.omninote.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.xueweiw.omninote.db.Column;
import edu.cmu.xueweiw.omninote.db.Column.DataType;
import edu.cmu.xueweiw.omninote.db.Table;; 

abstract public class Model implements Serializable {
	@Column(name = "_id", type = DataType.INTEGER, pk = true)
	protected Integer id;

	public Model() {
	}

	public Model(Integer id) {
		this.id = id;
	}

	public String tableName() {
		return getClass().getAnnotation(Table.class).name();
	}

	public String columnName(String fieldName) {
		Class<?> cls = getClass();
		Field f = null;
		while (f == null && cls != null) {
			try {
				f = cls.getDeclaredField(fieldName);
				if (f.isAnnotationPresent(Column.class)) {
					return f.getAnnotation(Column.class).name();
				}
			} catch (NoSuchFieldException e) {	
			}
			if (cls.getSimpleName().equals("Model")){
				break;
			}
			cls = cls.getSuperclass();
		}
		return null;
	}

	public String idColumnName() {
		return columnName("id");
	}


	public Map<String, String> toKeyValueMap() {
		Map<String, String> keyValuePair = new HashMap<String, String>();
		for (Field f : getClass().getDeclaredFields()) {
			Column c = f.getAnnotation(Column.class);
			if (c != null && !c.pk()) {
				Object value = null;
				try {
					f.setAccessible(true);
					value = f.get(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (value != null) {
					if ( c.type() == DataType.TEXT || c.type() == DataType.DATE || c.type() == DataType.DATETIME )
						keyValuePair.put(c.name(), "'" + value.toString() + "'");
					else 
						keyValuePair.put(c.name(), value.toString());
						
				}
			}
		}
		return keyValuePair;
	}

	public void setFieldsByResultSet(ResultSet result) {
		try {
			setId(result.getInt(columnName("id")));
			for (Field f : getClass().getDeclaredFields()) {
				if (f.isAnnotationPresent(Column.class)) {
					Column c = f.getAnnotation(Column.class);
					int index = result.findColumn(c.name());
					DataType type = c.type();
					f.setAccessible(true); 
					
						switch (type) {
						case INTEGER:
							f.set(this, result.getInt(index));
							break;
						case BOOLEAN:
							f.set(this, result.getBoolean(index));
							break;
						case BIGINT:
							f.set(this, result.getLong(index));
							break;
						case REAL:
						case DOUBLE:
							f.set(this, result.getDouble(index));
							break;
						case FLOAT:
							f.set(this, result.getFloat(index));
							break;
						case TEXT:
							f.set(this, result.getString(index));
							break;
						case DATE:
							f.set(this, result.getDate(index));
							break;
						case DATETIME:
							f.set(this, result.getTimestamp(index));
					}
				}
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
