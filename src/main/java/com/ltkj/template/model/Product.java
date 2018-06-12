package com.ltkj.template.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int price;
	private int quantity;

}
