package com.a702.feelingfilling.domain.request.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Yesterday extends Stat {
	int hour;
	public Yesterday(String emotion, int hour, int amount){
		super(emotion,amount);
		this.hour = hour;
		
	}
}