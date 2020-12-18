package tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping;

public class StatisticResultMapping {
    private Object name;
    private Object value;

    public StatisticResultMapping() {
        super();
    }

    public StatisticResultMapping(Object name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getName() {
        return this.name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}