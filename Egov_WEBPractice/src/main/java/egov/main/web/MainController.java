package egov.main.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@RequestMapping(value="/main.do")
	public String main(HttpServletRequest request, ModelMap model) {
		
		return "main/main";
	}
	
	@RequestMapping(value="/main2.do")
	public String main2(HttpServletRequest request, ModelMap model) {
		
		return "main/main2";
	}
	
	@RequestMapping(value="/main3.do")
	public String main3(@RequestParam("pw")String pw, HttpServletRequest request, ModelMap model) {
		
		String id = request.getParameter("id");
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		if(id.equals("qwer")) {
			model.addAttribute("userid", pw);
			
		}
		else {
			model.addAttribute("userid", pw);
		}
		
		userNo+=5;
		model.addAttribute("userNo", userNo);
		
		return "main/main3";
	}
	
	@RequestMapping(value="/main4/{userNo}.do")
	public String main4(@PathVariable String userNo, HttpServletRequest request, ModelMap model) {
		
		model.addAttribute("userNo", userNo);
		
		return "main/main3";
	}
}
