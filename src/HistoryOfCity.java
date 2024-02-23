public class HistoryOfCity {
    private String nameCity;
    private String date;
    private int temperature;

    public HistoryOfCity(String nameCity, String date, int temperature) {
        this.nameCity = nameCity;
        this.date = date;
        this.temperature = temperature;
    }

    public String getNameCity() {
        return nameCity;
    }

    public String getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }


    public String toString() {
        System.out.println("-----------------------------------");
        System.out.println("\tVille : "+this.getNameCity());
        System.out.println("\tTemperature : "+this.getTemperature());
        System.out.println("\tDate : "+this.getDate());
        return null;
    }
}
