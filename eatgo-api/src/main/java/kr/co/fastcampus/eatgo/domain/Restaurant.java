package kr.co.fastcampus.eatgo.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;



    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItems;

//    public Restaurant() {
//    }

//    public Restaurant(String name, String address) {
//        this.name = name;
//        this.address = address;
//    }
//    public Restaurant(Long id, String name, String address) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//    }


//    public void setId(long id) {
//        this.id = id;
//    }

//    public Long getId() {
//        return id;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
    public String getInformation() {
        return name + "in" + address;
    }

//    public List<MenuItem> getMenuItems() {
//        return menuItems;
//    }

//    public void addMenuItem(MenuItem menuItem) {
//
//        menuItems.add(menuItem);
//    }


    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);

    }

    public void updateInformation(String name, String address) {
        this.name =name;
        this.address = address;
    }
}
