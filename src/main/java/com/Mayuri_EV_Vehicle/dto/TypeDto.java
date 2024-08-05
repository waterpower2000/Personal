package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.TypeList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {

    private String id;
    private TypeList type;

}
