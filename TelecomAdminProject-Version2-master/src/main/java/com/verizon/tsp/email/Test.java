package com.verizon.tsp.email;

import org.springframework.beans.factory.*;  
import org.springframework.beans.factory.xml.XmlBeanFactory;  
import org.springframework.core.io.*;  
public class Test {   
public void sendMail(String emailId) {  
      
	String mesg="Hi User, \n This account has been idle for more than 3 months. \n We have deactivated your account. Visit your nearest store to get more details. \n \n \n \n Regards \n HarishChandran \n AdminTeam \n Skynet Inc   ";
Resource r=new ClassPathResource("applicationContext.xml");  
BeanFactory b=new XmlBeanFactory(r);  
MailMail m=(MailMail)b.getBean("mailMail");  
String sender="verizondummymail4@gmail.com";//write here sender gmail id  
String[] receiver={emailId};  
m.sendMail(sender,receiver,"Account Has been Deactivated",mesg);  
      
System.out.println("success");  
}  
}  