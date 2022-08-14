package safisoft.fyta;



public class Library {
    private int id;
    private String plant_name;
    private String plant_des;
    private String plant_img;






    public Library(int id, String plant_name, String plant_des, String plant_img) {
        this.id = id;
        this.plant_name = plant_name;
        this.plant_des = plant_des;
        this.plant_img = plant_img;
    }

    public int getId() {
        return id;
    }

    public String get_plant_name() {
        return plant_name;
    }

    public String get_plant_des() {
        return plant_des;
    }

    public String get_plant_img() { return plant_img; }






}
