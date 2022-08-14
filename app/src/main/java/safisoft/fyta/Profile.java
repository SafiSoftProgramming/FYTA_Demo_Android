package safisoft.fyta;

/**
 * Created by Belal on 10/18/2017.
 */


public class Profile {
    private int id;
    private String user_name;
    private String user_rank;
    private String member_date;
    private String location;
    private String plant_count;
    private String beams_count;
    private String profile_pic;
    private String rank_icon;


    public Profile(int id, String user_name, String user_rank, String member_date, String location, String plant_count, String beams_count, String profile_pic, String rank_icon) {
        this.id = id;
        this.user_name = user_name;
        this.user_rank = user_rank;
        this.member_date = member_date;
        this.location = location;
        this.plant_count = plant_count;
        this.beams_count = beams_count;
        this.profile_pic = profile_pic;
        this.rank_icon = rank_icon;

    }

    public int getId() { return id; }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public String getMember_date() {
        return member_date;
    }

    public String getLocation() {
        return location;
    }

    public String getPlant_count() {
        return plant_count;
    }

    public String getBeams_count() {
        return beams_count;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getRank_icon() {
        return rank_icon;
    }


}
