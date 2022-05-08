package myProject.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Api(value = "UserInfoClass")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ApiModelProperty(allowableValues = "100030499")
    int id;

    @ApiModelProperty(allowableValues = "77")
    int age;

    @ApiModelProperty(allowableValues = "Sam")
    String name;

    @ApiModelProperty(allowableValues = "Nate")
    String email;
    @OneToOne
    @JsonIgnore
    private User user;

    public UserInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
