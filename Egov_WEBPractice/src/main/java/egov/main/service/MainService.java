package egov.main.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface MainService {

	HashMap<String, Object> selectMain(HttpServletRequest request)throws Exception;

}
