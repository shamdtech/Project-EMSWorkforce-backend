package com.employee.user.service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Salary {

	private int id;

	private int userId;

	private String payCycle;

	private String paymentMode;

	private String bank;

	private String bankAccount;

	private String bankIfsc;

	private BigDecimal salary;

	private String fromDate;

	private String toDate;

}
