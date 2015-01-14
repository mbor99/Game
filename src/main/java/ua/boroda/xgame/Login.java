package ua.boroda.xgame;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ua.boroda.xgame.Utils.writeProperties;


public class Login {

    public static WebDriver driver;

    static String f;
    static String e;
    static String enemy;
    static int en;
    static int fleet;
    static int exp;
    static String serverLink = "http://xgame-online.com/uni1/";

    public void loginToServer() throws InterruptedException {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://xgame-online.com" + "/");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("тттттт");

        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("magnat");

        driver.findElement(By.xpath(".//*[@id='uni']/option[@value='1']")).click();

        driver.findElement(By.id("view")).click();
    }


//    @Test
//    public void testrun() {
//        WebDriver w = new FirefoxDriver();
//        w.navigate().to("http://ukr.net");
//    }

    public void closeBrowser() {
        driver.close();
    }

    public void transport(String ships, String planet) {
        try {
            driver.navigate().to(serverLink + "fleet.php");

            new Select(driver.findElement(By.xpath(".//*[@id='resources']/tbody/tr[4]/td[5]/select"))).selectByVisibleText(planet);

            Thread.sleep(2000);

            driver.findElement(By.name("ship203")).sendKeys(ships);   //бт
            driver.findElement(By.xpath("//td[2]/input")).click();
            Thread.sleep(2000);

            driver.findElement(By.name("galaxy")).clear();
            driver.findElement(By.name("galaxy")).sendKeys("4");
            Thread.sleep(2000);

            driver.findElement(By.name("system")).clear();
            driver.findElement(By.name("system")).sendKeys("617");
            Thread.sleep(2000);

            driver.findElement(By.name("planet")).clear();
            driver.findElement(By.name("planet")).sendKeys("4");
            Thread.sleep(2000);

            driver.findElement(By.id("submit")).click();
            driver.findElement(By.id("inpuT_0")).click(); //транспорт
            Thread.sleep(2000);

            driver.findElement(By.xpath("html/body/center[2]/center/form/table/tbody/tr[4]/th[2]/table/tbody/tr[7]/td/a/font")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("submit")).click();

            System.out.println("Транспорт с " + planet + " улетел ))");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Транспорт с " + planet + " не улетел ((");
        }
    }

    public boolean ataka(String system, String planet, String ships, String battleships, String battleshipType) {
        try {

            driver.navigate().to(serverLink + "fleet.php");

            new Select(driver.findElement(By.xpath(".//*[@id='resources']/tbody/tr[4]/td[5]/select"))).selectByVisibleText("Б5 [4:617:4]");

            driver.findElement(By.name("ship202")).clear();
            driver.findElement(By.name("ship202")).sendKeys(ships);    // малый танкер
            driver.findElement(By.name(battleshipType)).clear();
            driver.findElement(By.name(battleshipType)).sendKeys(battleships);       //эсминцы   "ship216"
            driver.findElement(By.xpath("//td[2]/input")).click();
            Thread.sleep(1000);

            driver.findElement(By.name("system")).clear();
            driver.findElement(By.name("system")).sendKeys(system);
            driver.findElement(By.name("planet")).clear();
            driver.findElement(By.name("planet")).sendKeys(planet);
            Thread.sleep(1000);

            driver.findElement(By.id("submit")).click();
            driver.findElement(By.id("inpuT_0")).click(); //атака
            Thread.sleep(1000);
            driver.findElement(By.id("submit")).click();

            System.out.println("Ataka na [4:" + system + ":" + planet + "] uletela ))");
            return true;
        } catch (Exception e) {
            System.out.println("Ataka ne uletela ((");
            return false;
        }
    }

    public void expeditsia() {
        try {
            driver.navigate().to(serverLink + "fleet.php");
            new Select(driver.findElement(By.xpath(".//*[@id='resources']/tbody/tr[4]/td[5]/select"))).selectByVisibleText("Б5 [4:617:4]");

            driver.findElement(By.name("ship202")).clear();
            driver.findElement(By.name("ship202")).sendKeys("15000");       //малый шатл

            driver.findElement(By.name("ship210")).clear();
            driver.findElement(By.name("ship210")).sendKeys("500");       // шпики

            driver.findElement(By.name("ship206")).clear();
            driver.findElement(By.name("ship206")).sendKeys("500");       //крейсер
//
//            driver.findElement(By.name("ship205")).clear();
//            driver.findElement(By.name("ship205")).sendKeys("100");       //фрегат
//            Thread.sleep(500);
//
//            driver.findElement(By.name("ship215")).clear();
//            driver.findElement(By.name("ship215")).sendKeys("100");       //линейки
//            Thread.sleep(500);
//
//            driver.findElement(By.name("ship216")).clear();
//            driver.findElement(By.name("ship216")).sendKeys("100");       //эсминец
//            Thread.sleep(500);
//
//            driver.findElement(By.name("ship204")).clear();
//            driver.findElement(By.name("ship204")).sendKeys("5000");       //корвет
//            Thread.sleep(800);
////
//            driver.findElement(By.name("ship217")).clear();
//            driver.findElement(By.name("ship217")).sendKeys("5");       //avik
//            Thread.sleep(500);
            driver.findElement(By.xpath("//td[2]/input")).click();
            Thread.sleep(1500);


            driver.findElement(By.name("planet")).clear();
            driver.findElement(By.name("planet")).sendKeys("16");
            Thread.sleep(1500);
            driver.findElement(By.id("submit")).click();


            driver.findElement(By.id("submit")).click();

            System.out.println("Expedicia uletela ))");

            int exx = Integer.parseInt(Utils.readProperties("exp"));
            writeProperties("exp", Integer.toString(exx + 1));

        } catch (Exception e) {
            System.out.println("Expedicia ne uletela ((");
        }
    }


    public static boolean spy(String gal, String system, String planet) {
        try {

            driver.navigate().to(serverLink + "fleet.php");
            Thread.sleep(2000);

            new Select(driver.findElement(By.xpath(".//*[@id='resources']/tbody/tr[4]/td[5]/select"))).selectByVisibleText("Б5 [4:617:4]");
            Thread.sleep(2000);

            driver.findElement(By.name("ship210")).sendKeys("3");
            Thread.sleep(2000);

            driver.findElement(By.xpath("//td[2]/input")).click();
            Thread.sleep(2000);

            driver.findElement(By.name("galaxy")).clear();
            driver.findElement(By.name("galaxy")).sendKeys(gal);
            Thread.sleep(2000);

            driver.findElement(By.name("system")).clear();
            driver.findElement(By.name("system")).sendKeys(system);
            Thread.sleep(2000);

            driver.findElement(By.name("planet")).clear();
            driver.findElement(By.name("planet")).sendKeys(planet);
            Thread.sleep(2000);

            driver.findElement(By.id("submit")).click();
            driver.findElement(By.id("inpuT_0")).click(); //шпионаж
            Thread.sleep(2000);
            driver.findElement(By.id("submit")).click();

            System.out.println("Shpioni na [" + gal + ":" + system + ":" + planet + "] uleteli :))");
            Thread.sleep(2000);

            try {
                Thread.sleep(10000);
                driver.navigate().to(serverLink + "fleet.php");
                Thread.sleep(2000);

                new Select(driver.findElement(By.xpath(".//*[@id='resources']/tbody/tr[4]/td[5]/select"))).selectByVisibleText("Б5 [4:617:4]");
                Thread.sleep(2000);
                driver.findElement(By.name("ship210")).sendKeys("3");
                Thread.sleep(2000);
                driver.findElement(By.xpath("//td[2]/input")).click();
                Thread.sleep(2000);
                //vibiraem kuda letet'

                driver.findElement(By.name("galaxy")).clear();
                driver.findElement(By.name("galaxy")).sendKeys(gal);
                Thread.sleep(2000);

                new Select(driver.findElement(By.name("planettype"))).selectByValue("3");
                Thread.sleep(2000);
                driver.findElement(By.name("system")).clear();
                driver.findElement(By.name("system")).sendKeys(system);
                Thread.sleep(2000);
                driver.findElement(By.name("planet")).clear();
                driver.findElement(By.name("planet")).sendKeys(planet);
                Thread.sleep(2000);

                //vibiraem typ missii( shpionash)


                driver.findElement(By.id("submit")).click();
                driver.findElement(By.id("inpuT_0")).click(); //шпионаж
                Thread.sleep(2000);
                driver.findElement(By.id("submit")).click();

                System.out.println("Shpioni na lunu  [" + gal + ":" + system + ":" + planet + "] uleteli :))");
                Thread.sleep(2000);

            } catch (Exception r) {
                System.out.println("Shpioni na lunu ne uliteli (vozmojno luni netu)");
            }

            return true;
        } catch (Exception e) {
            System.out.println("Shpioni ne uleteli");
            return false;
        }
    }


    public void research(String number) throws InterruptedException {
        timerSec(1);
        driver.navigate().to(serverLink + "buildings.php?mode=research&cmd=search&tech=" + number);
        System.out.println("Исследование запущено");
    }

    static public void timerMin(int minutes) throws InterruptedException {
        for (int i = minutes; i > 0; i--) {
            System.out.println("Ostalos " + i + " min do vipolnenia");
            timerSec(60);
        }
    }

    static public void timerSec(int sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }

    public static void getExpAndFleetFree() {
        driver.navigate().to(serverLink + "fleet.php");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Не получилось выяснить количество экспедиций и флотов");
        }

        try {
            f = driver.findElement(By.xpath("html/body/center[2]/table[1]/tbody/tr[2]/td[1]")).getText();
            e = driver.findElement(By.xpath("html/body/center[2]/table[1]/tbody/tr[2]/td[5]")).getText();

            fleet = Integer.parseInt(f.substring(6, f.length() - 6));
            exp = Integer.parseInt(e.substring(11, e.length() - 5));
            System.out.println("Нету флотов в полёте");
        } catch (Exception e0) {
            try {
                f = driver.findElement(By.xpath("html/body/center[2]/table[2]/tbody/tr[2]/td[1]")).getText();
                e = driver.findElement(By.xpath("html/body/center[2]/table[2]/tbody/tr[2]/td[5]")).getText();
                fleet = Integer.parseInt(f.substring(6, f.length() - 6));
                exp = Integer.parseInt(e.substring(11, e.length() - 5));
            } catch (Exception e1) {
                System.out.println("Не получилось получить количество свободных флотов");
            }
        }


    }

    public static void getEnemyFleet() {
        driver.navigate().to(serverLink + "frames.php");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("Не получилось выяснить количество вражеских флотов");
        }

        driver.switchTo().frame("Hauptframe");

        try {

            enemy = driver.findElement(By.xpath("/html/body/center[2]/center/table/tbody/tr[3]/td[2]/a")).getText();
            System.out.println("enemy = " + enemy);
            en = Integer.parseInt(enemy.substring(11, enemy.length() - 7));
        } catch (Exception e2) {
            System.out.println("Soobshenia vverhu netu");
        }

        try {
            enemy = driver.findElement(By.xpath("/html/body/center[2]/center/table/tbody/tr[2]/td[2]/a")).getText();
            System.out.println("enemy = " + enemy);
            en = Integer.parseInt(enemy.substring(11, enemy.length() - 7));
        } catch (Exception e2) {
            System.out.println("Soobshenia vverhu est");
        }

        driver.switchTo().defaultContent();
        System.out.println("en = " + en);
    }

    public static int getFleetFree() {
        getExpAndFleetFree();
        return fleet;
    }

    public static int getExpFree() {
        getExpAndFleetFree();
        return exp;
    }

    public static int getEnemy() {
        getEnemyFleet();
        return en;
    }

    //
    public static void main(String[] args) throws InterruptedException, IOException {
        Login r = new Login();
        r.loginToServer();
//        r.expeditsia();
//        uploadResources();
//        runResearch();
//        spy("617", "13");
//        scanInactivePlanet(r, "3", 1, 999);
//        scanInactivePlanet(r, "4", 317, 917);
//        scanInactivePlanet(r, "5", 1, 999);
    }

    public static void runResearch() throws InterruptedException {

//        Utils.sendSMS();

        timerMin(14 * 60 + 25);
//
        Login l = new Login();
        l.loginToServer();

        l.research("114");

//        timerMin(14 * 60 + 50);

//        l.research("139");

    }

    public static void scanInactivePlanet(Login login, String galaxy, int firstSystem, int lastSystem) throws InterruptedException {

        WebElement inputGalaxy;
        WebElement inputSystem;
        String status;
        String system;
        String vacation;

        int quantity = lastSystem - firstSystem;
        int planet;
        int counter = 0;
        List<PlanetCoordinat> list = new ArrayList<PlanetCoordinat>();

        Thread.sleep(3000);
        driver.navigate().to(serverLink + "galaxy.php?mode=0");
        Thread.sleep(3000);

        for (int s = 0; s < quantity; s++) {
            system = Integer.toString(firstSystem + s);

            inputSystem = driver.findElement(By.name("system"));
            inputGalaxy = driver.findElement(By.name("galaxy"));

            inputGalaxy.clear();
            inputGalaxy.sendKeys(galaxy);
            Thread.sleep(500);
            inputSystem.clear();
            inputSystem.sendKeys(system); // the value we want to set to input
            inputSystem.sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            for (int i = 1; i < 16; i++) {
                try {
                    vacation = null;
                    status = driver.findElement(By.xpath(".//*[@id='galaxy_form']/table[2]/tbody/tr[" + i + "]/th[7]/span/a/span")).getText();

                    try {
                        vacation = driver.findElement(By.xpath(".//*[@id='galaxy_form']/table[2]/tbody/tr[" + i + "]/th[7]/span/span")).getText();
                    } catch (Exception e1) {
                        Thread.sleep(50);
                    }


                    if (status.length() == 1 && status.equals("i") && vacation == null) {
                        planet = i - 1;
                        System.out.println(status + " [" + galaxy + ":" + system + ":" + planet + "]");
                        list.add(counter, new PlanetCoordinat(galaxy, system, String.valueOf(planet)));
                        counter++;

                    }
                } catch (Exception e) {
                    Thread.sleep(50);
                }
            }
            Thread.sleep(1000);
        }

        for (PlanetCoordinat aList : list) {
            spy(aList.getGal(), aList.getSys(), aList.getPl());
            Thread.sleep(10000);

        }
        System.out.println("Skanirovanie zakon4eno");
    }

    public static void uploadResources() {
        driver.navigate().to(serverLink + "allykasse.php");
        String maxResToUpload = "811287";
//        driver.findElement(By.name("metall")).clear();
//        driver.findElement(By.name("metall")).sendKeys(maxResToUpload);
//        driver.findElement(By.name("kristall")).clear();
//        driver.findElement(By.name("kristall")).sendKeys(maxResToUpload);
        driver.findElement(By.name("deuterium")).clear();
        driver.findElement(By.name("deuterium")).sendKeys(maxResToUpload);
        driver.findElement(By.name("submit")).click();
    }


    public static boolean checkIsTargetActive(String galaxy, String system, String planet) throws InterruptedException {
        WebElement inputGalaxy;
        WebElement inputSystem;
        String status;

        int p = Integer.valueOf(planet) + 1;

        driver.navigate().to(serverLink + "galaxy.php?mode=0");

        inputSystem = driver.findElement(By.name("system"));
        inputGalaxy = driver.findElement(By.name("galaxy"));

        inputGalaxy.clear();
        inputGalaxy.sendKeys(galaxy);
        inputSystem.clear();
        inputSystem.sendKeys(system); // the value we want to set to input
        driver.findElement(By.xpath(".//*[@type='submit']")).click();//

        try {
            status = driver.findElement(By.xpath(".//*[@id='galaxy_form']/table[2]/tbody/tr[" + p + "]/th[7]/span/a/span")).getText();
            if (status.length() == 0 && status.equals("")) {
                System.out.println(" [" + galaxy + ":" + system + ":" + planet + "]" + " is active");
                return true;
            }
        } catch (Exception e) {
            System.out.println(" [" + galaxy + ":" + system + ":" + planet + "]" + " is active");
            return true;
        }

        System.out.println("status = " + status + " [" + galaxy + ":" + system + ":" + planet + "]" + " is inactive");
        return false;
    }
}
