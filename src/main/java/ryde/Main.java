package ryde;

public class Main {

    public static void main(String[] args) throws Exception {
        String excelInputPath="C:/Users/Ryde/Desktop/test.xlsx";//excel输入路径
        String excelOutputPath="C:/Users/Ryde/Desktop/testOut.xlsx";//excel输出路径
        Integer sheetIndex=0;//sheet下标
        String targetURLPrefix="https://celnet-2e-dev-ed.my.salesforce.com";//目标SF链接前缀
        String objectName="Customer__c";//对象
        String token="00D5g000001FCQO!AR8AQBB5d9ksE.QozQj69nW8sznkepWfnc5E0gdpS_FEpWgvSAi69A2lPV1RZSIsma26r0OcaTx9YF2bQ5R5.ix1mmgd4Ku6";
        ExcelUtil.createFieldsByXSSF(excelInputPath,excelOutputPath,targetURLPrefix,objectName,sheetIndex,token);
    }
}
