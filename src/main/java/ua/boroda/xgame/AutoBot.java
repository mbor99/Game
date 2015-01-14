package ua.boroda.xgame;

import java.io.IOException;

import static ua.boroda.xgame.Login.*;


class AutoBot {

    private static int counter = 0;
    private static int fleetFree;
    private static int expFree;
    private static int free;
    private static int temp = 0;
    private static String ships;
    private static String battleships;
    private static String battleshipsType;
    private static String sys;
    private static String pos;
    static String resourceFolderLocal;

    //static String resourceFolderLocal="C:\\xgame\\"; //для Васи

    private static String[][] data;             //для запуска на Васе
//    static String[][] data = new Utils().readXLS("L:\\Education\\DBTask\\XGame\\src\\main\\resources\\planet.xls", "List");

    public static void main(String[] args) throws IOException, InterruptedException {
        String arg1 = args[0];
        System.out.println("arg1 = " + arg1);
        if (arg1.equals("0")) {
            resourceFolderLocal = "L:\\Education\\DBTask\\XGame\\src\\main\\resources\\";
        } else {
            resourceFolderLocal = arg1;
        }
        data = new Utils().readXLS(resourceFolderLocal + "planet.xls", "List");
        AutoBot a = new AutoBot();

        for (int i = 0; i < 200; i++) {
            try {
                a.openBrowserAndGame(a);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Something happened with browser");
            } finally {
                timerMin(20);
            }
        }
    }


    void openBrowserAndGame(AutoBot a) throws InterruptedException, IOException {
        Login login = new Login();
        login.loginToServer();
        a.launchBot(login);
    }


    void launchBot(Login login) throws IOException, InterruptedException {
        int max = 35;

        for (int t = 0; t < 10000; t++) {
            fleetFree = getFleetFree();
            expFree = getExpFree();
            if (fleetFree < max) {
                free = max - fleetFree;
                System.out.println("Free slots = " + free);
                for (int f = 0; f < free; f++) {
                    if (expFree < 4) {
                        int ex = 4 - expFree;
                        if (free < ex) {
                            ex = free;
                        }
                        System.out.println("Free expedition slots = " + ex);
                        for (int k = 0; k < ex; k++) {
                            sendExpedition(login);
                        }
                        if (ex > 1) f = f + ex;
                        expFree = getExpFree();
                        continue;
                    }
                    counter = Integer.parseInt(Utils.readProperties("counter"));
                    checkLastCounter();
                    if (data[counter][1].equals("transport")) {
                        sendTransport(login, data[counter][2], data[counter][3]);
                        continue;
                    }
                    if (data[counter][6].equals("0")) {
                        increaseCounter();
                        f = f - 1;
                        continue;
                    }
                    if (counter % 499 == 0 && counter != 0) { //проверка неактивных планет
//                        timerMin(70);
                        //  scanInactivePlanet(login);
                    }
                    if (counter % 15 == 0) {
                        uploadResources();
                    }
                    if (checkIsTargetActive()) {
                        increaseCounter();
                    } else {
                        sendOneFleet(login);
                    }

                }
            }
            getEnemy();

            if (en > max) {           // проверка наличия атаки
                if (temp == 0) {
                    Utils.sendSMS();
                }
                temp = temp + 1;
                if (temp == 20) {
                    temp = 0;
                }
            }
            Utils.timerMin(5);
        }
    }

    private boolean checkIsTargetActive() throws InterruptedException {

        String info = data[counter][1];
        String delims = "[:]";
        String[] u = info.split(delims);
        sys = u[0];
        pos = u[1];

       boolean active = Login.checkIsTargetActive("4",u[0],u[1]);
        return active;
    }

    private static void checkLastCounter() throws IOException {
        if (data[counter][0].equals("0000")) {
            Utils.writeProperties("counter", Integer.toString(0));
            counter = Integer.parseInt(Utils.readProperties("counter"));
        }
    }

    private static void sendTransport(Login login, String ships, String planet) throws InterruptedException, IOException {
        login.transport(ships, planet);
        Thread.sleep(7000);
        increaseCounter();
    }

    private static void sendExpedition(Login login) throws IOException {
        login.expeditsia();
    }

    private static void increaseCounter() throws IOException {
        counter = Integer.parseInt(Utils.readProperties("counter"));
        Utils.writeProperties("counter", Integer.toString(counter + 1));
        checkLastCounter();
    }

    private static void sendOneFleet(Login login) throws IOException, InterruptedException {


        counter = Integer.parseInt(Utils.readProperties("counter"));

        ships = data[counter][2];
        battleships = data[counter][4]; //количество боевых кораблей
        battleshipsType = data[counter][5]; //тип боевого корабля
        String info = data[counter][1];
        String delims = "[:]";
        String[] u = info.split(delims);
        sys = u[0];
        pos = u[1];

        if (login.ataka(sys, pos, ships, battleships, battleshipsType)) {
            Utils.writeProperties("counter", Integer.toString(counter + 1));
            checkLastCounter();
        }
        Thread.sleep(3000);

    }

    private static void scanInactivePlanet(Login login) throws InterruptedException {
        Login.scanInactivePlanet(login, "3", 1, 999);
        Login.scanInactivePlanet(login, "4", 317, 917);
        Login.scanInactivePlanet(login, "5", 1, 999);
    }

    private static void uploadResources() {
        try {
            Login.uploadResources();
        } catch (Exception e) {
            System.out.println("ne polu4ilos polojit resursi v alyans");
        }

    }


}
