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
	//�ύ���ݿ�
	void commitDB(DataBase db) throws FileNotFoundException, IOException;
}
