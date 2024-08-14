import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BucketController {

    @Autowired
    private BucketService bucketService;

    @PostMapping("/buckets/{bucketId}/transitionToState2")
    public void transitionBucketToState2(@PathVariable String bucketId) {
        bucketService.transitionBucketToState2(bucketId);
    }

    @PostMapping("/buckets/{bucketId}/transitionToState3")
    public void transitionBucketToState3(@PathVariable String bucketId) {
        bucketService.transitionBucketToState3(bucketId);
    }
}