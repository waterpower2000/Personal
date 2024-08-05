package com.Mayuri_EV_Vehicle.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class SalesDataReadExcel {
	
	private static final Map<String, Integer> sheetRecord = new HashMap<>();

    private static final Map<String, Sheet> sheets = new HashMap<>();
    private final DataSource dataSource;

    public SalesDataReadExcel(DataSource dataSource)
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
	
	public void writePurchaseData(Workbook workbook,String reg) {
        if(reg.equals("ALL")) {
            String sql = "SELECT tb_sales.order_id,tb_sales.region_name,(tb_sales.total_price-tb_sales.hsrf_charge-tb_sales.reg_charge-tb_sales.hypo_charge-tb_sales.license_charge-tb_sales.ins_charge-tb_sales.gst) as PRICE,tb_sales.hsrf_charge,tb_sales.hypo_charge,tb_sales.ins_charge,tb_sales.reg_charge,tb_sales.license_charge,tb_sales.gst,tb_sales.total_price,tb_sales.created_on,tb_e_auto_variants_details.chassis_number,tb_e_auto_variants_details.controller_name ,tb_e_auto_variants.colour,tb_e_auto_variants.variant_name FROM ((tb_sales INNER JOIN tb_e_auto_variants_details ON tb_sales.e_auto_variant_details = tb_e_auto_variants_details.e_auto_variant_details_id) INNER JOIN tb_e_auto_variants ON tb_e_auto_variants_details.vehicle = tb_e_auto_variants.e_auto_variant_id);";
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
                row.createCell(0).setCellValue("order_id");
                row.createCell(1).setCellValue("region_name");
                row.createCell(2).setCellValue("PRICE");
                row.createCell(3).setCellValue("hsrf_charge");
                row.createCell(4).setCellValue("hypo_charge");
                row.createCell(5).setCellValue("ins_charge");
                row.createCell(6).setCellValue("reg_charge");
                row.createCell(7).setCellValue("license_charge");
                row.createCell(8).setCellValue("gst");
                row.createCell(9).setCellValue("total_price");
                row.createCell(10).setCellValue("created_on");
                row.createCell(11).setCellValue("chassis_number");
                row.createCell(12).setCellValue("controller_name");
                row.createCell(13).setCellValue("colour");
                row.createCell(14).setCellValue("variant_name");

                //Row row = sheet.createRow(0);
                int i = 1;
                while (resultSet.next()) {
                    Row header = sheet.createRow(i++);
                    header.createCell(0).setCellValue(resultSet.getString("order_id"));
                    header.createCell(1).setCellValue(resultSet.getString("region_name"));
                    header.createCell(2).setCellValue(resultSet.getString("PRICE"));
                    header.createCell(3).setCellValue(resultSet.getString("hsrf_charge"));
                    header.createCell(4).setCellValue(resultSet.getString("hypo_charge"));
                    header.createCell(5).setCellValue(resultSet.getString("ins_charge"));
                    header.createCell(6).setCellValue(resultSet.getString("reg_charge"));
                    header.createCell(7).setCellValue(resultSet.getString("license_charge"));
                    header.createCell(8).setCellValue(resultSet.getString("gst"));
                    header.createCell(9).setCellValue(resultSet.getString("total_price"));
                    header.createCell(10).setCellValue(resultSet.getString("created_on"));
                    header.createCell(11).setCellValue(resultSet.getString("chassis_number"));
                    header.createCell(12).setCellValue(resultSet.getString("controller_name"));
                    header.createCell(13).setCellValue(resultSet.getString("colour"));
                    header.createCell(14).setCellValue(resultSet.getString("variant_name"));
                }

                //sheetRecord.putAll(integer);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CommonUtil.closeResource(connection);
            }
        }
        else {
            String sql = "SELECT tb_sales.order_id,(tb_sales.total_price-tb_sales.hsrf_charge-tb_sales.reg_charge-tb_sales.hypo_charge-tb_sales.license_charge-tb_sales.ins_charge-tb_sales.gst) as PRICE,tb_sales.hsrf_charge,tb_sales.hypo_charge,tb_sales.ins_charge,tb_sales.reg_charge,tb_sales.license_charge,tb_sales.gst,tb_sales.total_price,tb_sales.created_on,tb_e_auto_variants_details.chassis_number,tb_e_auto_variants_details.controller_name ,tb_e_auto_variants.colour,tb_e_auto_variants.variant_name FROM ((tb_sales INNER JOIN tb_e_auto_variants_details ON tb_sales.e_auto_variant_details = tb_e_auto_variants_details.e_auto_variant_details_id) INNER JOIN tb_e_auto_variants ON tb_e_auto_variants_details.vehicle = tb_e_auto_variants.e_auto_variant_id) where tb_sales.region_name=?";
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, reg);
                ResultSet resultSet = preparedStatement.executeQuery();
                Sheet sheet = workbook.createSheet("Purchase");
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("order_id");
                row.createCell(1).setCellValue("PRICE");
                row.createCell(2).setCellValue("hsrf_charge");
                row.createCell(3).setCellValue("hypo_charge");
                row.createCell(4).setCellValue("ins_charge");
                row.createCell(5).setCellValue("reg_charge");
                row.createCell(6).setCellValue("license_charge");
                row.createCell(7).setCellValue("gst");
                row.createCell(8).setCellValue("total_price");
                row.createCell(9).setCellValue("created_on");
                row.createCell(10).setCellValue("chassis_number");
                row.createCell(11).setCellValue("controller_name");
                row.createCell(12).setCellValue("colour");
                row.createCell(13).setCellValue("variant_name");

                int i = 1;
                while (resultSet.next()) {
                    Row header = sheet.createRow(i++);
                    header.createCell(0).setCellValue(resultSet.getString("order_id"));
                    header.createCell(1).setCellValue(resultSet.getString("PRICE"));
                    header.createCell(2).setCellValue(resultSet.getString("hsrf_charge"));
                    header.createCell(3).setCellValue(resultSet.getString("hypo_charge"));
                    header.createCell(4).setCellValue(resultSet.getString("ins_charge"));
                    header.createCell(5).setCellValue(resultSet.getString("reg_charge"));
                    header.createCell(6).setCellValue(resultSet.getString("license_charge"));
                    header.createCell(7).setCellValue(resultSet.getString("gst"));
                    header.createCell(8).setCellValue(resultSet.getString("total_price"));
                    header.createCell(9).setCellValue(resultSet.getString("created_on"));
                    header.createCell(10).setCellValue(resultSet.getString("chassis_number"));
                    header.createCell(11).setCellValue(resultSet.getString("controller_name"));
                    header.createCell(12).setCellValue(resultSet.getString("colour"));
                    header.createCell(13).setCellValue(resultSet.getString("variant_name"));
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
