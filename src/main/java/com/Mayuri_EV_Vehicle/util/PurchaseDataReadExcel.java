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
public class PurchaseDataReadExcel {
	
	private static final Map<String, Integer> sheetRecord = new HashMap<>();

    private static final Map<String, Sheet> sheets = new HashMap<>();
    private final DataSource dataSource;

    public PurchaseDataReadExcel(DataSource dataSource)
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
	
	public void writePurchaseData(Workbook workbook, String reg) {

        if(reg.equals("ALL"))
        {
            String sql ="SELECT tb_purchase.order_id, tb_purchase.per_quantity_price,tb_purchase.gst_price,tb_purchase.total_price,tb_purchase.created_on,tb_purchase.region_name,tb_e_auto_variants_details.chassis_number,tb_e_auto_variants_details.controller_name ,tb_e_auto_variants.colour,tb_e_auto_variants.variant_name FROM ((tb_purchase INNER JOIN tb_e_auto_variants_details ON tb_purchase.e_auto_variant_details = tb_e_auto_variants_details.e_auto_variant_details_id) INNER JOIN tb_e_auto_variants ON tb_e_auto_variants_details.vehicle = tb_e_auto_variants.e_auto_variant_id)";
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                Sheet sheet = workbook.createSheet("Purchase");
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("order_id");
                row.createCell(1).setCellValue("per_quantity_price");
                row.createCell(2).setCellValue("gst_price");
                row.createCell(3).setCellValue("total_price");
                row.createCell(4).setCellValue("created_on");
                row.createCell(5).setCellValue("region_name");
                row.createCell(6).setCellValue("chassis_number");
                row.createCell(7).setCellValue("controller_name");
                row.createCell(8).setCellValue("colour");
                row.createCell(9).setCellValue("variant_name");

                //Row row = sheet.createRow(0);
                int i=1;
                while(resultSet.next())
                {
                    Row header= sheet.createRow(i++) ;
                    header.createCell(0).setCellValue(resultSet.getString("order_id"));
                    header.createCell(1).setCellValue(resultSet.getString("per_quantity_price"));
                    header.createCell(2).setCellValue(resultSet.getString("gst_price"));
                    header.createCell(3).setCellValue(resultSet.getString("total_price"));
                    header.createCell(4).setCellValue(resultSet.getString("created_on"));
                    header.createCell(5).setCellValue(resultSet.getString("region_name"));
                    header.createCell(6).setCellValue(resultSet.getString("chassis_number"));
                    header.createCell(7).setCellValue(resultSet.getString("controller_name"));
                    header.createCell(8).setCellValue(resultSet.getString("colour"));
                    header.createCell(9).setCellValue(resultSet.getString("variant_name"));
                }

                //sheetRecord.putAll(integer);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CommonUtil.closeResource(connection);
            }
        }
        else {
            String sql ="SELECT tb_purchase.order_id, tb_purchase.per_quantity_price,tb_purchase.gst_price,tb_purchase.total_price,tb_purchase.created_on,tb_purchase.region_name,tb_e_auto_variants_details.chassis_number,tb_e_auto_variants_details.controller_name ,tb_e_auto_variants.colour,tb_e_auto_variants.variant_name FROM ((tb_purchase INNER JOIN tb_e_auto_variants_details ON tb_purchase.e_auto_variant_details = tb_e_auto_variants_details.e_auto_variant_details_id) INNER JOIN tb_e_auto_variants ON tb_e_auto_variants_details.vehicle = tb_e_auto_variants.e_auto_variant_id) WHERE tb_purchase.region_name = ?";
            Connection connection = getConnection();
//        CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv"), '\t');
//        Boolean includeHeaders = true;
//
//        java.sql.ResultSet myResultSet = .... //your resultset logic here
//
//        writer.writeAll(myResultSet, includeHeaders);
//
//        writer.close();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, reg);
                ResultSet resultSet = preparedStatement.executeQuery();
                Sheet sheet = workbook.createSheet("Purchase");

                //String className = resultSet.getString("group_name");
                //Sheet sheet = getSheet(workbook, className);

                //int integer = 6;
                //Row header = sheet.createRow(integer);
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("order_id");
                row.createCell(1).setCellValue("per_quantity_price");
                row.createCell(2).setCellValue("gst_price");
                row.createCell(3).setCellValue("total_price");
                row.createCell(4).setCellValue("created_on");
                row.createCell(5).setCellValue("region_name");
                row.createCell(6).setCellValue("chassis_number");
                row.createCell(7).setCellValue("controller_name");
                row.createCell(8).setCellValue("colour");
                row.createCell(9).setCellValue("variant_name");

                //Row row = sheet.createRow(0);
                int i=1;
                while(resultSet.next())
                {
                    Row header= sheet.createRow(i++) ;
                    header.createCell(0).setCellValue(resultSet.getString("order_id"));
                    header.createCell(1).setCellValue(resultSet.getString("per_quantity_price"));
                    header.createCell(2).setCellValue(resultSet.getString("gst_price"));
                    header.createCell(3).setCellValue(resultSet.getString("total_price"));
                    header.createCell(4).setCellValue(resultSet.getString("created_on"));
                    header.createCell(5).setCellValue(resultSet.getString("region_name"));
                    header.createCell(6).setCellValue(resultSet.getString("chassis_number"));
                    header.createCell(7).setCellValue(resultSet.getString("controller_name"));
                    header.createCell(8).setCellValue(resultSet.getString("colour"));
                    header.createCell(9).setCellValue(resultSet.getString("variant_name"));
                }

                //sheetRecord.putAll(integer);

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
