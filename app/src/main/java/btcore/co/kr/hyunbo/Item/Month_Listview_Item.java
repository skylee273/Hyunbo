package btcore.co.kr.hyunbo.Item;

/**
 * Created by leehaneul on 2017-09-27.
 */

public class Month_Listview_Item {



    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getDefault_time() {
        return default_time;
    }

    public void setDefault_time(String default_time) {
        this.default_time = default_time;
    }

    public String getOver_time() {
        return over_time;
    }

    public void setOver_time(String over_time) {
        this.over_time = over_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String work_time;
    private String out_time;
    private String default_time;
    private String over_time;
    private String state;
    private String night;
    private String special;

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSpecial_over() {
        return special_over;
    }

    public void setSpecial_over(String special_over) {
        this.special_over = special_over;
    }

    private String special_over;
}

