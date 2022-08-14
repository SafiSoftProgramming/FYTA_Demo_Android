package safisoft.fyta;

/**
 * Created by Belal on 10/18/2017.
 */



public class Gardens {
    private String id;
    private String plant_name;
    private String plant_place;
    private String plant_pic;
    private String connect_state;
    private String water_state;
    private String nutrient_state;
    private String light_state;
    private String temp_state;



    public Gardens(String id, String plant_name,String plant_place,String plant_pic,String connect_state,String water_state,String nutrient_state,String light_state,String temp_state) {
        this.id = id;
        this.plant_name = plant_name;
        this.plant_place = plant_place;
        this.plant_pic = plant_pic;
        this.connect_state = connect_state;
        this.water_state = water_state;
        this.nutrient_state = nutrient_state;
        this.light_state = light_state;
        this.temp_state = temp_state;

    }

    public String getId() {
        return id;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public String getPlant_place() {
        return plant_place;
    }

    public String getPlant_pic() {
        return plant_pic;
    }

    public String getConnect_state() {
        return connect_state;
    }

    public String getWater_state() {
        return water_state;
    }

    public String getNutrient_state() {
        return nutrient_state;
    }

    public String getLight_state() {
        return light_state;
    }

    public String getTemp_state() {
        return temp_state;
    }


}
