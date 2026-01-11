package egov.board.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egov.board.service.BoardService;

@Controller
public class BoardController {
	
	@Resource(name="BoardService")BoardService boardService;
	
	@RequestMapping(value="/boardWrite.do")
	public String boardWrite(HttpServletRequest request,ModelMap model)
	{
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			boardService.checkUser(request);
		} catch (Exception e) {
			
			//로그기록,상태코드반환 또는 에러페이지 전달
			String error = e.getMessage();
			if (error.equals("No_Login")) {
				return "redirect:/login.do";
			}
			return "error/error";
		}
		return "board/boardwrite";
	}

	@RequestMapping(value="/boardInsert.do")
	public String boardInsert(HttpServletRequest request,ModelMap model)
	{
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			boardService.saveBoard(request);
		} catch (Exception e) {
			
			//로그기록,상태코드반환 또는 에러페이지 전달
			String error = e.getMessage();
			if (error.equals("No_Login")) {
				return "redirect:/login.do";
			}
			else if(error.equals("Long_Title")){
				return "redirect:/boardwrite.do";
			}
			return "error/error";
		}
		return "board/boardwrite";
	}
	
	@RequestMapping(value="/boardView.do")
	public String boardView(HttpServletRequest request,ModelMap model)
	{
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = boardService.showBoard(request);
		} catch (Exception e) {
			
			//로그기록,상태코드반환 또는 에러페이지 전달
			String error = e.getMessage();
			if (error.equals("No_Login")) {
				return "redirect:/login.do";
			}
			else if(error.equals("No_valid")){
				return "redirect:/boardlist.do";
			}
			else if(error.equals("resultError_idnotFound")){
				return "redirect:/boardlist.do";	
			}
			return "error/error";
		}
		model.addAttribute("userid", resultMap.get("userid").toString());
		model.addAttribute("title", resultMap.get("title").toString());
		model.addAttribute("boardcontents", resultMap.get("boardcontents").toString());
		return "board/boardview";
	}
	
	@RequestMapping(value="/boardList.do")
	public String boardList(HttpServletRequest request,ModelMap model)
	{
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			list = boardService.showBoardList(request);
		} catch (Exception e) {
			
			//로그기록,상태코드반환 또는 에러페이지 전달
			String error = e.getMessage();
			if (error.equals("No_Login")) {
				return "redirect:/login.do";
			}
			else if(error.equals("No_valid")){
				return "redirect:/boardlist.do";
			}
			else if(error.equals("resultError_idnotFound")){
				return "redirect:/boardlist.do";	
			}
			return "error/error";
		}
		
		model.addAllAttributes(list.get(list.size()-1));
		list.remove(list.size()-1);
		model.addAttribute("boardlist",list);
		
	    System.out.println("model contents = " + model);
	    System.out.println("paginationInfo in model = " + model.get("paginationInfo"));

		
		return "board/boardlist2";
	}
}
