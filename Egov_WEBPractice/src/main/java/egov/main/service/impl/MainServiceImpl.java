package egov.main.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import com.lib.model.UserVO;

import egov.main.dao.MainMapper;
import egov.main.service.MainService;

@Service("MainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService{

	@Resource(name="MainMapper")
	MainMapper mainMapper;
	
	@Override
	public HashMap<String, Object> selectMain(HttpServletRequest request) throws Exception {
		HashMap<String,Object> paramMap = new HashMap();
		//request요청을 paramMap에 담아주기
		
		return mainMapper.selectMain(paramMap);
	}
	
	@Override
	public HashMap<String, Object> checkLogin(HttpServletRequest request) throws Exception {
		
		String userid = request.getParameter("id");
	    String userpw = request.getParameter("pw"); 
	    
	    // 유효성검사
	    if(userid == null || userid.length() > 10) {
	        throw new Exception("validError_userId");
	    }
	    
	    HashMap<String, Object> paramMap = new HashMap<>();
	    paramMap.put("IN_USERID", userid);
	    paramMap.put("IN_USERPW", userpw);
	    paramMap.put("OUT_STATE", 0);
	    
	    System.out.println("Service paramMap BEFORE = " + paramMap);
	    mainMapper.checkLogin(paramMap);
	    System.out.println("Service paramMap AFTER = " + paramMap);
	    
	    //  HashMap<String, Object> resultMap = mainMapper.checkLogin(paramMap); MyBatis 구조때문에 resultMap은 항상 null
	    
	    // OUT_STATE 체크
	    if ((Integer)paramMap.get("OUT_STATE") != 0) {
	        throw new Exception("resultError_idnotFound");
	    }

	    List<UserVO> list =
	    	    (List<UserVO>) paramMap.get("REF_CURSOR");

    	if (list == null || list.isEmpty()) {
    	    throw new Exception("resultError_idnotFound");
    	}
    	
    	UserVO userVO = list.get(0);
    	
    	HashMap<String, Object> resultMap = new HashMap();
    	resultMap.put("uservo", userVO);
    	
    	return resultMap;
	}

}
