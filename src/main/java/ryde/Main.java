package ryde;

import java.io.*;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {

        String excelInputPath="C:\\Users\\Ryde\\Desktop\\createField.xlsx";//excel输入路径
        String excelOutputPath="C:\\Users\\Ryde\\Desktop\\createField.xlsx";//excel输出路径
        Integer sheetIndex=0;//sheet下标
        String targetURLPrefix="https://leo--full.sandbox.my.salesforce.com";//目标SF链接前缀
        String objectName="t_visit__c";//对象
        String token="00DIn000000LOjdMAG!AQEAQAFA18ycpHr4d5nSLhBILO3mhSunpwlGWpeO.19EiiXKtHwN4SJ7mBjoIiZaWMxzM47CKsRXp.zmlHyBaU1FMMP5CQBu";
        ExcelUtil.createFieldsByXSSF(excelInputPath,excelOutputPath,targetURLPrefix,objectName,sheetIndex,token);

//        test();
//        test2();
    }

    public static void test(){
        for (int i = 1; i <= 200; i++) {
            System.out.print("选项" + i + "&" + i+';');
        }
    }
    public static void test2() throws Exception {
        FileInputStream fileInputStream1 = new FileInputStream("C:\\Users\\Ryde\\Desktop\\Print_SalesQuoteExcel_CN - 2022-04-19T154406.253.xls");
        FileInputStream fileInputStream2 = new FileInputStream("C:\\Users\\Ryde\\Desktop\\Print_SalesQuoteExcel_CN (8).xls");
        System.out.println(inputStream2Base64(fileInputStream1).equalsIgnoreCase(inputStream2Base64(fileInputStream2)));
    }

    private static String inputStream2Base64(InputStream is) throws Exception {
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new Exception("输入流关闭异常");
                }
            }
        }

        return Base64.getEncoder().encodeToString(data);
    }
}
