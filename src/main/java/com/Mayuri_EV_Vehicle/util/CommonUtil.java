package com.Mayuri_EV_Vehicle.util;

import java.sql.Connection;

public class CommonUtil {
	
	public static void closeResource(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (Exception e) {}
    }

}
