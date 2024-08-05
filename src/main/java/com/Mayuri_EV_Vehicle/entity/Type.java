package com.Mayuri_EV_Vehicle.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="type")
@Getter
@Setter
@NoArgsConstructor
public class Type {

    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
    @GeneratedValue(generator = "Application-Generic-Generator")
    private String id;

    @Enumerated(EnumType.STRING)
    private TypeList type;


    public Type(String id, TypeList type) {
        this.id = id;
        this.type = type;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeList getType() {
        return type;
    }

    public void setType(TypeList type) {
        this.type = type;
    }


}
