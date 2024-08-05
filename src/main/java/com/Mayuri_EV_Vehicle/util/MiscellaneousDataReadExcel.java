package com.Mayuri_EV_Vehicle.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class MiscellaneousDataReadExcel {
	
	private static final Map<String, Integer> sheetRecord = new HashMap<>();

    private static final Map<String, Sheet> sheets = new HashMap<>();
    private final DataSource dataSource;

    public MiscellaneousDataReadExcel(DataSource dataSource)
    {
    	this.dataSource = dataSource;
    }

	
	private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public void writePurchaseData(Workbook workbook, DomainUser domainUser) {
        String reg;
        reg=domainUser.getRegion_name();
        if(reg.equals("ALL")) {
            String sql = "SELECT * FROM tb_miscellaneous;";
//        CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv"), '\t');
//        Boolean includeHeaders = true;
//
//        java.sql.ResultSet myResultSet = .... //your resultset logic here
//
//        writer.writeAll(myResultSet, includeHeaders);
//
//        writer.close();
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                Sheet sheet = workbook.createSheet("Purchase");

                //String className = resultSet.getString("group_name");
                //Sheet sheet = getSheet(workbook, className);

                //int integer = 6;
                //Row header = sheet.createRow(integer);
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("miscellaneous_id");
                row.createCell(1).setCellValue("amount");
                row.createCell(2).setCellValue("date");
                row.createCell(3).setCellValue("money_type");
                row.createCell(4).setCellValue("specification");
                row.createCell(5).setCellValue("created_on");
                //row.createCell(6).setCellValue("expense_type");
                row.createCell(6).setCellValue("region");

                //Row row = sheet.createRow(0);
                int i = 1;
                while (resultSet.next()) {
                    Row header = sheet.createRow(i++);
                    header.createCell(0).setCellValue(resultSet.getString("miscellaneous_id"));
                    header.createCell(1).setCellValue(resultSet.getString("amount"));
                    header.createCell(2).setCellValue(resultSet.getString("date"));
                    header.createCell(3).setCellValue(resultSet.getString("money_type"));
                    header.createCell(4).setCellValue(resultSet.getString("specification"));
                    header.createCell(5).setCellValue(resultSet.getString("created_on"));
                    //header.createCell(6).setCellValue(resultSet.getString("expense_type"));
                    header.createCell(6).setCellValue(resultSet.getString("region"));
                }

                //sheetRecord.putAll(integer);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CommonUtil.closeResource(connection);
            }
        }
        else {
            String sql = "SELECT * FROM tb_miscellaneous where region=?;";
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, reg);
                ResultSet resultSet = preparedStatement.executeQuery();
                Sheet sheet = workbook.createSheet("Purchase");
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("miscellaneous_id");
                row.createCell(1).setCellValue("amount");
                row.createCell(2).setCellValue("date");
                row.createCell(3).setCellValue("money_type");
                row.createCell(4).setCellValue("specification");
                row.createCell(5).setCellValue("created_on");
                row.createCell(6).setCellValue("region");

                int i = 1;
                while (resultSet.next()) {
                    Row header = sheet.createRow(i++);
                    header.createCell(0).setCellValue(resultSet.getString("miscellaneous_id"));
                    header.createCell(1).setCellValue(resultSet.getString("amount"));
                    header.createCell(2).setCellValue(resultSet.getString("date"));
                    header.createCell(3).setCellValue(resultSet.getString("money_type"));
                    header.createCell(4).setCellValue(resultSet.getString("specification"));
                    header.createCell(5).setCellValue(resultSet.getString("created_on"));
                    header.createCell(6).setCellValue(resultSet.getString("region"));
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CommonUtil.closeResource(connection);
            }
        }
    }
	
	
	public static Sheet getSheet(Workbook workbook, String className) {
        if(sheets.containsKey(className)) {
            return sheets.get(className);
        }
        Sheet sheet = workbook.createSheet(className);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Class_ID");
        header.createCell(1).setCellValue("Student_ID");
        header.createCell(2).setCellValue("ROLL NO");
        header.createCell(3).setCellValue("ADMISSION NO");
        header.createCell(4).setCellValue("STUDENT NAME");
        header.createCell(5).setCellValue("STUDENT PHONE");

        sheets.put(className, sheet);
        sheetRecord.put(className, 0);
        return sheet;
    }

}
