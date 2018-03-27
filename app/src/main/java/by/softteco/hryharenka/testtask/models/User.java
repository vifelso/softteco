package by.softteco.hryharenka.testtask.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "user")
public class User implements Serializable {
    @DatabaseField(columnName = "id")
    @SerializedName("id")
    @Expose
    private Integer id;
    @DatabaseField(columnName = "user_name")
    @SerializedName("name")
    @Expose
    private String name;
    @DatabaseField(columnName = "username")
    @SerializedName("username")
    @Expose
    private String username;
    @DatabaseField(columnName = "email")
    @SerializedName("email")
    @Expose
    private String email;
    // Foreign key defined to hold associations
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    @SerializedName("address")
    @Expose
    private Address address;
    @DatabaseField(columnName = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;
    @DatabaseField(columnName = "website")
    @SerializedName("website")
    @Expose
    private String website;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    @SerializedName("company")
    @Expose
    private Company company;

    public User() {
    }

    @DatabaseTable(tableName = "geo")
    public static class Geo implements Serializable {
        @DatabaseField(generatedId = true, columnName = "id_geo")
        private int idGeo;
        @DatabaseField(columnName = "lat")
        @SerializedName("lat")
        @Expose
        private String lat;
        @DatabaseField(columnName = "lng")
        @SerializedName("lng")
        @Expose
        private String lng;
        private final static long serialVersionUID = -1826920932038227997L;

        public Geo() {
        }

        public int getIdGeo() {
            return idGeo;
        }

        public void setIdGeo(int idGeo) {
            this.idGeo = idGeo;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

    }

    @DatabaseTable(tableName = "companies")
    public static class Company implements Serializable {

        @DatabaseField(generatedId = true, columnName = "id_company")
        private int companyId;
        @DatabaseField(columnName = "name")
        @SerializedName("name")
        @Expose
        private String name;
        @DatabaseField(columnName = "catchPhrase")
        @SerializedName("catchPhrase")
        @Expose
        private String catchPhrase;
        @DatabaseField(columnName = "bs")
        @SerializedName("bs")
        @Expose
        private String bs;
        private final static long serialVersionUID = 3681691534446782194L;

        public Company() {
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

    }

    @DatabaseTable(tableName = "address")
    public static class Address implements Serializable {
        public Address() {
        }

        @DatabaseField(generatedId = true, columnName = "id_address")
        private int addressId;
        @DatabaseField(columnName = "street")
        @SerializedName("street")
        @Expose
        private String street;
        @DatabaseField(columnName = "suite")
        @SerializedName("suite")
        @Expose
        private String suite;
        @DatabaseField(columnName = "city")
        @SerializedName("city")
        @Expose
        private String city;
        @DatabaseField(columnName = "zipcode")
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @DatabaseField(foreign = true, foreignAutoRefresh = true)
        @SerializedName("geo")
        @Expose
        private Geo geo;
        private final static long serialVersionUID = -1189117856793849977L;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (website != null ? !website.equals(user.website) : user.website != null) return false;
        return company != null ? company.equals(user.company) : user.company == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}