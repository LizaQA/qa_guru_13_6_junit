package guru.qa;

public enum Sex {
    MALE("Мужской"), FEMALE("Женский");

    public final String desc;

    Sex(String desc) {
        this.desc = desc;
    }
}
