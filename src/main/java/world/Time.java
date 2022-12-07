package world;

public final class Time {
    private static Time instance;
    public int hour;
    public int minute;

    private Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    private Time() {

    }

    public void addMinute() {
        if(minute < 59){
            minute++;
        } else {
            minute = 0;
            if(hour < 23){
                hour++;
            } else {
                hour = 0;
            }
        }
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static Time getInstance(int hour, int minute) {
        if(instance == null) {
            instance = new Time(hour, minute);
        }
        return instance;
    }

    public static Time getInstance() {
        if(instance == null) {
            instance = new Time();
        }
        return instance;
    }

    @Override
    public String toString() {
        String strHour = (hour >= 10 ? "":"0") + hour;
        String strMinute = (minute >= 10 ? "":"0") + minute;
        return strHour + ":" + strMinute;
    }
}
