package ua.boroda.xgame;

class PlanetCoordinat {
    PlanetCoordinat(String gal, String sys, String pl) {
        this.pl = pl;
        this.sys = sys;
        this.gal = gal;
    }

    public String getPl() {
        return pl;
    }

    public String getSys() {
        return sys;
    }

    public String getGal() {
        return gal;
    }

    private String pl;
    private String sys;
    private String gal;
}