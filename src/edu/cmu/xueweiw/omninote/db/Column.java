package edu.cmu.xueweiw.omninote.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String name() ;
	DataType type() ;
	boolean pk() default false;
	
	enum DataType {	INTEGER,BIGINT,REAL,DOUBLE,FLOAT,TEXT,DATE,BOOLEAN, DATETIME }

}
