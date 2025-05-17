/**
 * 
 */
package com.st.server;

import com.st.util.DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author asus
 *
 */
public interface DBService {
	//提交数据库
	void commitDB(DataBase db) throws FileNotFoundException, IOException;
}
