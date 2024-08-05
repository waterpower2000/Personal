package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockTransferDto {
	@NotEmpty(message = "Type Should not be Empty")
	private String type;
	@NotEmpty(message = "Type Should not be Empty")
	private String eriksha_variant_details_id;
	@NotEmpty
	private String region;

}
