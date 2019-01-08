package si.fri.rso.smartarticle.collections.services.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppProperties {

    @ConfigValue(value = "collection-services.enabled", watch = true)
    private boolean collectionServicesEnabled;

    @ConfigValue(value = "collection-article-services.enabled", watch = true)
    private boolean collectionArticleServicesEnabled;

    public boolean isCollectionServicesEnabled() {
        return collectionServicesEnabled;
    }

    public void setCollectionServicesEnabled(boolean collectionServicesEnabled) {
        this.collectionServicesEnabled = collectionServicesEnabled;
    }

    public boolean isCollectionArticleServicesEnabled() {
        return collectionArticleServicesEnabled;
    }

    public void setCollectionArticleServicesEnabled(boolean collectionArticleServicesEnabled) {
        this.collectionArticleServicesEnabled = collectionArticleServicesEnabled;
    }
}
