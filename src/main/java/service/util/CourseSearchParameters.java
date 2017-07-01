package service.util;

/**
 * Created by Ярослав on 09.06.2017.
 */
public class CourseSearchParameters {

    private String type;

    private String location;

    private double minPrice;

    private double maxPrice;

    private boolean onlyFree;

    public CourseSearchParameters setType(String type) {
        this.type = type;
        return this;
    }

    public CourseSearchParameters setLocation(String location) {
        this.location = location;
        return this;
    }

    public CourseSearchParameters setMinPrice(double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public CourseSearchParameters setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public CourseSearchParameters setOnlyFree(boolean onlyFree) {
        this.onlyFree = onlyFree;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public boolean isOnlyFree() {
        return onlyFree;
    }

    @Override
    public String toString() {
        return "CourseSearchParameters{" +
                "type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", onlyFree=" + onlyFree +
                '}';
    }
}
