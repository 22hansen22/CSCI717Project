package com.jcg.spring.hibernate.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jcg.spring.hibernate.pojo.User;
import com.jcg.spring.hibernate.service.AuthService;

@Controller
@RequestMapping("/user")
public class LoginCtrl {

	@Autowired
	private AuthService authenticateService;			// This will auto-inject the authentication service into the controller.

	private static Logger log = Logger.getLogger(LoginCtrl.class);

	// Checks if the user credentials are valid or not.
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ModelAndView validateUsr(@RequestParam("username")String username,@RequestParam("password")String password) {
		String msg = "";
		String type="";
		boolean isValid = authenticateService.findUser(username, password);
		log.info("Is user valid?= " + isValid);

		if(isValid) {
			msg = "Welcome " + username + "!";
			User u=authenticateService.findUser2(username, password);
			type=u.getType();
		} else {
			msg = "Invalid credentials";
			type="none";
		}
		
		log.info("What type of user?= " + type);
		//return new ModelAndView("result", "output", msg);	//output is the attribute
		
		ModelAndView mv=new ModelAndView();
		if(type.equals("student"))
			mv.setViewName("resultStudent.jsp");
		else if (type.equals("teacher"))
			mv.setViewName("resultTeacher.jsp");
		else
			//default to old result
			mv.setViewName("result.jsp");
		
		mv.addObject("output", msg);
		mv.addObject("type", type);
		
		return mv;
	}
}