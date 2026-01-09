package egov.board.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import com.lib.util.Validation_Form;

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

	@Override
	public HashMap<String, Object> showBoard(HttpServletRequest request) throws Exception {	    
	    if (request.getSession().getAttribute("myid") == null) {
			throw new Exception("No_Login");
		}
	    
		String brdid = request.getParameter("brdid");
	    boolean validNumber = false;
	    validNumber = Validation_Form.validNum(brdid);
	    
	    if (validNumber == false) {
	    	throw new Exception("No_valid");
		}
	    
	    HashMap<String, Object> paramMap = new HashMap<>();
	    paramMap.put("in_brdid", brdid);
	    paramMap.put("out_state", 0);
	    
	    try {
	        boardMapper.showBoard(paramMap);
	    } catch (Exception e) {
	        System.out.println("DB 호출 중 에러: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    }
	    
	    
	    // OUT_STATE 체크
	    if ((Integer)paramMap.get("out_state") != 0) {
	        throw new Exception("resultError_idnotFound");
	    }	

	    List<HashMap<String, Object>> list =
	            (List<HashMap<String, Object>>) paramMap.get("REF_CURSOR");

	    if (list == null || list.isEmpty()) {
	        throw new Exception("resultError_idnotFound");
	    }

	    return list.get(0);
	}
}
