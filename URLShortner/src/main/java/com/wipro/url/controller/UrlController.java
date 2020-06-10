package com.wipro.url.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.wipro.url.model.UrlMapping;
import com.wipro.url.model.User;
import com.wipro.url.service.UrlGeneratorService;
import com.wipro.url.service.UrlMappingService;
import com.wipro.url.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="URlShortener Controller API")
public class UrlController {

	private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

	private static final String baseUri = "oauth2/authorize-client";
    Map<String, String> urls = new HashMap<>();
    
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    
    @Autowired
    private OAuth2AuthorizedClientService clientService;
    
    @Autowired
    UserService service;
    
    @Autowired
	private UrlGeneratorService urlGeneratorService;
    
    @Autowired
    private UrlMappingService urlService;
    
    ModelAndView mv=new ModelAndView();
    String name = "";
    
    @SuppressWarnings("unchecked")
	@GetMapping("/")
    @ApiOperation(value="Returns login page")
    public ModelAndView getLogin() {
    	Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
            .as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> urls.put(registration.getClientName(), baseUri + "/" + registration.getRegistrationId()));
        logger.info("Registered providers:{}",urls);
        mv.addObject("urls", urls);
        mv.setViewName("login");
    	return mv;
    	
    }
    
    @GetMapping("/success")
    @ApiOperation(value="Returns login success page")
    public ModelAndView getLoginData(Model model, OAuth2AuthenticationToken token) {
    	logger.info("Authentication success");
    	OAuth2AuthorizedClient client=clientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
    	String userInfo=client.getClientRegistration()
    			.getProviderDetails()
    			.getUserInfoEndpoint()
    			.getUri();
    	if (!StringUtils.isEmpty(userInfo)) {
    		RestTemplate restTemplate = 	new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                .getTokenValue());

            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            @SuppressWarnings("rawtypes")
			ResponseEntity<Map> response = restTemplate.exchange(userInfo, HttpMethod.GET, entity, Map.class);
            String clientname=client.getClientRegistration().getClientName();
            Map<?, ?> userAttributes = response.getBody();
            System.out.println(userAttributes);
            String username;
            if(clientname.equalsIgnoreCase("Github"))
               username=(String) userAttributes.get("login");
            else
            	username=(String) userAttributes.get("name");
            if(!service.existsById(username)) {
            User user=new User(username,clientname);
            logger.info("saving user:{}",user);
            service.saveUser (user);
            }
            name=username;
            mv.addObject("name", username);
            mv.setViewName("loginSuccess");
    	}
    	return mv;
    }
    
    @GetMapping("/failure")
    @ApiOperation(value="Returns login failure page")
    public ModelAndView loginFailure() {
    	mv.setViewName("loginFailure");
    	logger.info("Authentication failed");
    	return mv;
    }
    
    @RequestMapping(value= "/url", method=RequestMethod.POST)
    @ApiOperation(value="Generates Url and returns shortened Url")
	public ModelAndView generateURL(@RequestParam("oldUrl") String oldUrl,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
    	logger.info("Generating Url");
    	logger.info("Old Url:{}",oldUrl);
		URL url;
		HttpURLConnection huc;
		try {
			 
			url = new URL(oldUrl);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
			int responseCode = huc.getResponseCode();
			System.out.println("Response Code : "+responseCode);
			if(responseCode == 404) {
				response.sendError(404);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			response.sendError(404);
		} catch (IOException e) {
			e.printStackTrace();
			response.sendError(404);
		}
		UrlMapping urlMap = urlService.findByOldUrl(oldUrl);
		String message = "";
		String newurl = "";
		if( urlMap != null) {
			logger.info("Url already present in database");
			message = "Already Url generated : ";
			newurl = urlMap.getNewUrl();
		}
    	
		else {
			User user = service.findByUserName(name);
			message = "Your new Url is : ";
			newurl = "https://shorturl/"+urlGeneratorService.getNewURL(oldUrl);
			urlService.addUrlDetails(newurl, oldUrl, user);
			logger.info("Url Details added");
		}
		ModelAndView mv1 = new ModelAndView();
		mv1.addObject("name", name);
		mv1.addObject("message", message);
		mv1.addObject("url", newurl);
        mv1.setViewName("loginSuccess");
        logger.info("New Url:{}",newurl);
        return mv1; 
	}
}
