package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/hello")
public class HelloEndpoint {

    @Autowired
    private GitHubService gitHubService;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<?> sayHello(@PathVariable String username) {

        DeferredResult<HelloMessage> result = new DeferredResult<>();

        gitHubService.getUser(username).subscribe((user) -> {
            result.setResult(new HelloMessage(String.format("Hello %s from %s!", user.getName(), user.getLocation())));

        }, (throwable) -> {
            result.setErrorResult(throwable.getMessage());
        });

        return result;
    }
}
