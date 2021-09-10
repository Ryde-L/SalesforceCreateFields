package ryde;

public class Main {

    public static void main(String[] args) throws Exception {
        String excelInputPath="C:\\Users\\Ryde\\Desktop\\视频传播链\\Share.xlsx";//excel输入路径
        String excelOutputPath="C:\\Users\\Ryde\\Desktop\\视频传播链\\Share.xlsx";//excel输出路径
        Integer sheetIndex=0;//sheet下标
        String targetURLPrefix="https://celnet--full.my.salesforce.com";//目标SF链接前缀
//        String targetURLPrefix="https://celnet-2e-dev-ed.my.salesforce.com";//目标SF链接前缀
        String objectName="VcStatShare__c";//对象
        String token="00D5D0000005Bgx!AR0AQEYYq9CRr5fTJmoKGfAZrWddcIET3fuGHivGR3KOXjW3yIopaxvF7StwNseTpT5cBg6hq1DImFSIk_ySeLKKR46C07l_";
        ExcelUtil.createFieldsByXSSF(excelInputPath,excelOutputPath,targetURLPrefix,objectName,sheetIndex,token);
    }
}
