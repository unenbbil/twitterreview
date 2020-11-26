package ch.zhaw.gpi.twitterreview;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Named("postUserAdapter")
public class PostUserDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String userName = (String) execution.getVariable("benutzername");
        String firstName = (String) execution.getVariable("vorname");

        JSONObject userAJsonObject = new JSONObject();
        userAJsonObject.put("userName", userName);
        userAJsonObject.put("firstName", firstName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>(userAJsonObject.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.exchange("http://localhost:8070/api/users", HttpMethod.POST, httpEntity, String.class);
        } catch (Exception e) {
            throw e;
        }

    }

}
