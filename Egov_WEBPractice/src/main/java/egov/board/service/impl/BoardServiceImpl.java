package egov.board.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egov.board.dao.BoardMapper;
import egov.board.service.BoardService;

@Service("BoardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService{

	@Resource(name="BoardMapper")
	BoardMapper boardMapper;

	@Override
	public void checkUser(HttpServletRequest request) throws Exception {
		
		if (request.getSession().getAttribute("myid") == null) {
			throw new Exception("No_Login");
		}
	}
	
	@Override
	public void saveBoard(HttpServletRequest request) throws Exception {
		
		if (request.getSession().getAttribute("myid") == null) {
			throw new Exception("No_Login");
		}
		String title = request.getParameter("title");
		String content = request.getParameter("mytextarea");
		// 요청을 데이터베이스로 전달
		if (title.length()>25) {
			throw new Exception("Long_Title");
		}
		
	    HashMap<String, Object> paramMap = new HashMap<>();
	    paramMap.put("in_title", title);
	    paramMap.put("in_content", content);
	    paramMap.put("in_userid", request.getSession().getAttribute("myid"));
	    paramMap.put("out_state", 2);
	    
	    try {
	        System.out.println("DB 호출 전");
	        boardMapper.saveBoard(paramMap);
	        System.out.println("DB 호출 후 - out_state: " + paramMap.get("out_state"));
	    } catch (Exception e) {
	        System.out.println("DB 호출 중 에러: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
	}
}
