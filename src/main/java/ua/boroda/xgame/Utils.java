package ua.boroda.xgame;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.Properties;

public class Utils extends AutoBot {

    String empty = "";

    public String[][] readXLS(String fileName, String xlsSheetName) {

        String[][] data;

        try {
            File datafile = new File(fileName);
            InputStream fis = new BufferedInputStream(new FileInputStream(datafile));
            HSSFWorkbook book = new HSSFWorkbook(fis);
            HSSFSheet sheet = book.getSheet(xlsSheetName);
            int rowNum = sheet.getLastRowNum() + 1;
            int colNum = sheet.getRow(0).getLastCellNum();
            data = new String[rowNum][colNum];

            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                try {

                    for (short j = 0; j < colNum; j++) {
                        HSSFCell cell = row.getCell(j);
                        String value = cellToString(cell);
                        data[i][j] = value;
                    }


                } catch (Exception e) {
                    System.out.println(i);
                }
            }
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String cellToString(HSSFCell cell) {
        Object result = null;
        String returnResult = null;
        try {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    result = cell.getRichStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    result = empty;
                    break;
                default:
                    // Assert.fail("xlsfile: could not read cell");
            }
            if (result != null) {
                returnResult = result.toString();
            }
            return returnResult;
        } catch (Exception e) {
            System.out.println("не получилось вычитать ексель");
        }
        return null;
    }

    public static String readProperties(String name) {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(resourceFolderLocal + "settings.properties");
            try {
                prop.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return prop.getProperty(name);

    }

    public static void writeProperties(String name, String value) throws IOException {

        try {
            FileInputStream fis = new FileInputStream(resourceFolderLocal + "settings.properties");
            Properties prop = new Properties();
            prop.load(fis);

            String c = prop.getProperty("counter");
            String x = prop.getProperty("exp");
            fis.close();

            File f = new File(resourceFolderLocal + "settings.properties");
            OutputStream out = new FileOutputStream(f);

            prop.setProperty("counter", c);
            prop.setProperty("exp", x);
            prop.setProperty(name, value);

            prop.store(out, "Update name properties");
            out.close();

        } catch (Exception e) {
            System.out.println("Не удалось записать проперти");
        }
    }

    static public void timerMin(int minutes) throws InterruptedException {
        for (int i = minutes; i > 0; i--) {
            System.out.print("Осталось " + i + " минут до выполнения");
            for (int j = 0; j < 60; j++) {
                Thread.sleep(1000);
                System.out.print("'");
            }
            System.out.println("");
        }
    }

    public static void sendSMS() {
        HttpClient httpClient = new HttpClient();

        Credentials credentials = new UsernamePasswordCredentials("ACe86ba88758702f9ba5aa96c7c23e692d", "e38c9d21efb89665671354c0ddaab2ee");
        httpClient.getState().setCredentials(AuthScope.ANY, credentials);

        PostMethod postMethod = new PostMethod("https://api.twilio.com/2010-04-01/Accounts/ACe86ba88758702f9ba5aa96c7c23e692d/SMS/Messages.json");

        postMethod.addParameter("From", "+12248032942");
        postMethod.addParameter("To", "+380934503593");
        postMethod.addParameter("Body", "Kto-to na menya letit ALARM ALARM");

        try {
            httpClient.executeMethod(postMethod);
            String response = postMethod.getResponseBodyAsString();
            System.out.println("response = " + response);
        } catch (IOException e) {
            System.out.println(" Не получилось отправить смс");
        }


    }

//    @Test
//    public static void uploadResources() {
//        int temp = 0;
//        temp = 789 % 500;
//        System.out.println(temp);
//    }


}
