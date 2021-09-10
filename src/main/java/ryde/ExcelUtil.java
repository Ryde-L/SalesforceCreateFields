package ryde;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcelUtil {
    static Map<String, Integer> titleMap = new HashMap<String, Integer>();
    static ExecutorService executorService = Executors.newFixedThreadPool(20);//可用线程池提高效率，暂未启用

    static {
        titleMap.put("API", null);
        titleMap.put("标签", null);
        titleMap.put("公式", null);
        titleMap.put("类型", null);
        titleMap.put("长度", null);
        titleMap.put("选项列表值", null);
        titleMap.put("默认值", null);
        titleMap.put("创建状态", null);
    }

    /**
     * 读取xlsx
     *
     * @param excelPath
     * @param objectName
     * @param sheetIndex
     * @param token
     * @throws Exception
     */
    public static void createFieldsByXSSF(String excelPath, String excelOutputPath, String targetURLPrefix, String objectName, Integer sheetIndex, String token) throws Exception {
        sheetIndex = sheetIndex == null ? 0 : sheetIndex;
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelPath));//读取上传的EXCEL
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);//SHEET

        //获取title
        XSSFRow titleRow = sheet.getRow(0);
        for (int i = 0; i < titleRow.getLastCellNum(); i++) {
            if (titleRow.getCell(i) != null && titleMap.containsKey(titleRow.getCell(i).toString()))
                titleMap.put(titleRow.getCell(i).toString(), i);
        }

        if (titleMap.get("标签") == null) {
            System.out.println("未找到标签所在列");
            return;
        }
        XSSFRow row = sheet.getRow(1);
        //按行读取字段
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            try {
                RequestBodyEntity entity = new RequestBodyEntity();
                row = sheet.getRow(i);
                if (row == null || row.getCell(titleMap.get("API")) == null || row.getCell(titleMap.get("类型")) == null)
                    continue;
                if(row.getCell(titleMap.get("创建状态"))!=null && "成功".equals(row.getCell(titleMap.get("创建状态")).toString()))
                    continue;
                entity.fullName = objectName + "." + FieldNameUtil.toCamelCase(row.getCell(titleMap.get("API")).toString())+"__c";
                entity.metadata.label = row.getCell(titleMap.get("标签")).toString();
                System.out.print(entity.metadata.label);
                entity.metadata.type = row.getCell(titleMap.get("类型")).toString();//英文type

                if (entity.metadata.type.contains("Text"))//文本/文本区/富文本
                    entity.metadata.length = Double.valueOf(row.getCell(titleMap.get("长度")).toString()).intValue();
                else if (entity.metadata.type.equalsIgnoreCase("Number") || entity.metadata.type.equalsIgnoreCase("Currency")) {
                    //数字/币种
                    String[] precisionAndScale = row.getCell(titleMap.get("长度")).toString().split(",");
                    entity.metadata.precision = Integer.parseInt(precisionAndScale[0]) + Integer.parseInt(precisionAndScale[1]);//总长度
                    entity.metadata.scale = Integer.valueOf(precisionAndScale[1]);//精度
                } else if (entity.metadata.type.equalsIgnoreCase("Picklist")) {
                    //单项列表
                    String[] pickValue = row.getCell(titleMap.get("选项列表值")).toString().split(";");
                    entity.metadata.valueSet = new ValueSet();
                    
                    ValueSet.ValueSetDefinition.Value[] values = new ValueSet.ValueSetDefinition.Value[pickValue.length];
                    for (int j = 0; j < pickValue.length; j++) {
                        ValueSet.ValueSetDefinition.Value v = new ValueSet.ValueSetDefinition.Value();
                        v.label = pickValue[j];
                        v.valueName = pickValue[j];
                        values[j] = v;
                    }
                    entity.metadata.valueSet.valueSetDefinition.value = values;
                } else if (entity.metadata.type.equalsIgnoreCase("lookup")) {
                    //查找
                } else if (entity.metadata.type.equalsIgnoreCase("rollup")) {
                    //累计汇总
                } else {
                    //其它
                }

                if (titleMap.get("公式") != null && row.getCell(titleMap.get("公式")) != null && !row.getCell(titleMap.get("公式")).toString().equals(""))
                    entity.metadata.formula = row.getCell(titleMap.get("公式")).toString();

                //executorService.execute();
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                if (HttpUtil.isCreateSuccessful(targetURLPrefix, token, JsonUtils.objectToJson(entity))) {
//                    cellStyle.setFillForegroundColor(IndexedColors.GREEN.index);
//                    System.out.println(row.getCell(titleMap.get("标签")) + "创建成功");
//                    row.getCell(titleMap.get("创建状态")).setCellValue("成功");
                    row.createCell(titleMap.get("创建状态")).setCellValue("成功");

                    System.out.println("成功");
                } else {
//                    cellStyle.setFillForegroundColor(IndexedColors.RED.index);
//                    System.out.println(row.getCell(titleMap.get("标签")) + "创建失败");
//                    row.getCell(titleMap.get("创建状态")).setCellValue("失败");
                    row.createCell(titleMap.get("创建状态")).setCellValue("失败");
                    System.out.println("失败");

                }
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                row.getCell(titleMap.get("标签")).setCellStyle(cellStyle);

            } catch (Exception e) {
                e.printStackTrace();
//                XSSFCellStyle cellStyle = workbook.createCellStyle();
//                cellStyle.setFillForegroundColor(IndexedColors.RED.index);
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                System.out.println(row.getCell(titleMap.get("标签")) + "创建失败");
//                row.getCell(titleMap.get("标签")).setCellStyle(cellStyle);
            }
        }
        //保存
        FileOutputStream os = new FileOutputStream(excelOutputPath == null || excelOutputPath.equals("") ? "D:\\result.xlsx" : excelOutputPath);
        workbook.write(os);
        os.close();
    }
}
