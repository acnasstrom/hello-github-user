package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;


@Service
public class GitHubService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubService.class);

    @Autowired
    private GitHubApi gitHubApi;

    @HystrixCommand(groupKey = "github", commandKey="getUser", fallbackMethod = "getUserFallback")
    public Observable<GitHubUser> getUser(String username) {
        return Observable.create(new Observable.OnSubscribe<GitHubUser>() {

            @Override
            public void call(Subscriber<? super GitHubUser> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        GitHubUser user = gitHubApi.getUser(username);
                        observer.onNext(user);
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    LOGGER.error("Error getting user from GitHub api", e.getMessage(), e);
                    observer.onError(e);
                }
            }

        });
    }

    public GitHubUser getUserFallback(String username) {
        LOGGER.info("Falling back to anonymous user");
        return new GitHubUser("Anonymous", "Nowhere");
    }

}