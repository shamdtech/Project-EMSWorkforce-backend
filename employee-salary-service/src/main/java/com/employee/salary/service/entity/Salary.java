package com.employee.salary.service.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NonNull
    private int userId;
    
	@NonNull
    private String payCycle; // Month, Quater, Year
    
	@NonNull
    private String paymentMode;  // Bank Transfer, UPI
    
	@NonNull
    private String bank;   // bank name
    
	@NonNull
    private String bankAccount;
    
	@NonNull
    private String bankIfsc;

	@NonNull
    private BigDecimal salary;
	
	@NonNull
    private String fromDate;
    
	@NonNull
    private String toDate;
	

}
