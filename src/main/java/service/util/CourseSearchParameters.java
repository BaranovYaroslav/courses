package service.util;

import dispatcher.HttpMatcher;
import entities.Course;

/**
 * Created by Ярослав on 09.06.2017.
 */
public class CourseSearchParameters {

    private String type;

    private String location;

    private double minPrice;

    private double maxPrice;

    private boolean onlyFree;

    public static Builder newBuider() {
        return new CourseSearchParameters().new Builder();
    }

    public class Builder {
        public Builder() {}

        public Builder setType(String type) {
            CourseSearchParameters.this.type = type;
            return this;
        }

        public Builder setLocation(String location) {
            CourseSearchParameters.this.location = location;
            return this;
        }

        public Builder setMinPrice(double minPrice) {
            CourseSearchParameters.this.minPrice = minPrice;
            return this;
        }

        public Builder setMaxPrice(double maxPrice) {
            CourseSearchParameters.this.maxPrice = maxPrice;
            return this;
        }

        public Builder setOnlyFree(boolean onlyFree) {
            CourseSearchParameters.this.onlyFree = onlyFree;
            return this;
        }

        public CourseSearchParameters build() {
            return CourseSearchParameters.this;
        }
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
