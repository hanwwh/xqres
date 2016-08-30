package com.restaurant.system.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.restaurant.system.service.interfaces.ISystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring-test.xml")
public class SystemServiceTest {

	@Autowired
	ISystemService systemService;
	
	@Before
	public void beforeF(){
		System.out.println("----->before");
	}
	
	@Test
	public void getNextValue(){
		long v = systemService.getNextValue("ORDER_ITEM");
		System.out.println("----->v: "+v);
	}
	
	@After
	public void afterF(){
		System.out.println("----->after");
	}
}
