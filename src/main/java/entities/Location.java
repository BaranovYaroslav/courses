package entities;

/**
 * Class that represents location entity.
 *
 * @author Yaroslav Baranov
 */
public class Location {

    private int id;

    private String city;

    private String address;

    private double xCoordinate;

    private double yCoordinate;

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public static Builder newBuilder() {
        return new Location().new Builder();
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (id != location.id) return false;
        if (Double.compare(location.xCoordinate, xCoordinate) != 0) return false;
        if (Double.compare(location.yCoordinate, yCoordinate) != 0) return false;
        if (address != null ? !address.equals(location.address) : location.address != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        temp = Double.doubleToLongBits(xCoordinate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yCoordinate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            Location.this.id = id;
            return this;
        }

        public Builder setCity(String city) {
            Location.this.city = city;
            return this;
        }

        public Builder setAddress(String address) {
            Location.this.address = address;
            return this;
        }

        public Builder setXCoordinate(double xCoordinate) {
            Location.this.xCoordinate = xCoordinate;
            return this;
        }

        public Builder setYCoordinate(double yCoordinate) {
            Location.this.yCoordinate = yCoordinate;
            return this;
        }

        public Location build() {
            return Location.this;
        }
    }
}
