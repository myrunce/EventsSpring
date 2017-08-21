package com.myron.Events.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myron.Events.models.Event;
import com.myron.Events.models.Message;
import com.myron.Events.models.Role;
import com.myron.Events.models.User;
import com.myron.Events.services.EventService;
import com.myron.Events.services.MessageService;
import com.myron.Events.services.UserService;
import com.myron.Events.validator.UserValidator;

@Controller
public class EventsController {
	private final UserService userService;
    private final UserValidator userValidator;
    private final EventService eventService;
    private final MessageService messageService;
     
    @Autowired
    public EventsController(UserService userService, UserValidator userValidator, EventService eventService, MessageService messageService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.eventService = eventService;
        this.messageService = messageService;
    }
    
//    public Users() {
//    	
//    }


//	@RequestMapping("/registration")
//	public String registerForm(@Valid @ModelAttribute("user") User user) {
//		return "registrationPage.jsp";
//	}
	
	String[] states = {"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
	
	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        // NEW
        userValidator.validate(user, result);
        if (result.hasErrors()) {
        	System.out.println(result);
            return "loginPage.jsp";
        }
        System.out.println("making user...");
        userService.saveWithUserRole(user);
        return "redirect:/";
    }



	@RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user) {
		model.addAttribute("states", states);
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        return "loginPage.jsp";
    }
	
	@RequestMapping("/")
	public String redirect() {
		return "redirect:/events";
	}
	
    @RequestMapping("/events")
    public String home(Principal principal, Model model, HttpSession session, @ModelAttribute("event") Event event, BindingResult result) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("currentUser", userService.findByUsername(username));
        model.addAttribute("states", states);
//        List<Object[]> a = eventService.eventsOutOfArea(user.getState());
//        System.out.println(a[0][5]);
        model.addAttribute("eventsInState", eventService.eventsInArea(user.getState()));
        model.addAttribute("eventsOutOfState", eventService.eventsOutOfArea(user.getState()));
        return "events.jsp";
    }
    
    @PostMapping("/events")
    public String addEvent(Model model, Principal principal, @Valid @ModelAttribute("event") Event event, BindingResult result){
    	if (result.hasErrors()) {
    		String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("currentUser", userService.findByUsername(username));
            model.addAttribute("states", states);
//            List<Object[]> a = eventService.eventsOutOfArea(user.getState());
//            System.out.println(a[0][5]);
            model.addAttribute("eventsInState", eventService.eventsInArea(user.getState()));
            model.addAttribute("eventsOutOfState", eventService.eventsOutOfArea(user.getState()));
            return "events.jsp";
        }else{
        	System.out.println("ADDING EVENT");
        	event.setHost(userService.findByUsername(principal.getName()));
            eventService.addEvent(event);
            return "redirect:/events";
        }
    }
    
    @RequestMapping("/{id}/join")
    public String joinEvent(@PathVariable("id") Long id, Principal principal) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	Event event = eventService.findOne(id);
    	List<User> peopleGoing = new ArrayList<User>();
    	peopleGoing.add(user);
    	event.setPeopleGoing(peopleGoing);
    	eventService.updateEvent(event);
    	return "redirect:/events";	
    }
    
    @RequestMapping("/{id}/cancel")
    public String leaveEvent(@PathVariable("id") Long id, Principal principal) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	Event event = eventService.findOne(id);
    	List<User> peopleGoing = event.getPeopleGoing();
    	peopleGoing.remove(user);
    	event.setPeopleGoing(peopleGoing);
    	eventService.updateEvent(event);
    	return "redirect:/events";	
    }
    
  
    @RequestMapping("/events/{id}")
    public String showEvent(@PathVariable("id") Long id, Model model, @ModelAttribute("m") Message message, BindingResult result) {
    	Event event = eventService.findOne(id);
    	List<Message> messages = messageService.findAllByEvent(id);
    	model.addAttribute("event", event);
    	model.addAttribute("messages", messages);
    	return "oneEvent.jsp";
    }
    
    
    @RequestMapping("/{id}/edit")
    public String editEvent(@PathVariable("id") Long id, Model model) {
    	Event event = eventService.findOne(id);
    	model.addAttribute("event", event);
    	model.addAttribute("states", states);
    	return "editEvent.jsp";
    }
    
    @PostMapping("/{id}/edit")
    public String postEditEvent(@PathVariable("id") Long id, Principal principal, @Valid @ModelAttribute("event") Event event, BindingResult result) {
    	if (result.hasErrors()) {
            return "redirect:/" + id + "/edit";
        }else{
        	System.out.println("UPDATING EVENT");
        	event.setHost(userService.findByUsername(principal.getName()));
            eventService.updateEvent(event);
            return "redirect:/events/" + id;
        }
    }
    
    @PostMapping("/events/{id}")
    public String addMessage(@PathVariable("id") Long id, Principal principal, @Valid @ModelAttribute("m") Message message, BindingResult result) {
    	if (result.hasErrors()) {
    		return "redirect:/events/" + id;
    	}
    	else {
	    	String username = principal.getName();
	    	User user = userService.findByUsername(username);
	    	Event event = eventService.findOne(id);
	    	message.setOp(user);
	    	message.setEvent(event);
	    	messageService.addMessage(message);
    	}
    	return "redirect:/events/" + id;
    }
    
    @RequestMapping("/{id}/delete")
    public String deleteEvent(@PathVariable("id") Long id) {
    	eventService.deleteEvent(eventService.findOne(id));
    	return "redirect:/events";
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        model.addAttribute("users", userService.findAll());
        return "adminPage.jsp";
    }
        
}
