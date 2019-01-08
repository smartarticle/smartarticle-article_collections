package si.fri.rso.smartarticle.collections.services.beans;


import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.smartarticle.collections.models.dtos.Article;
import si.fri.rso.smartarticle.collections.models.entities.Collection;
import si.fri.rso.smartarticle.collections.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;


@RequestScoped
public class CollectionsBean {

    private Logger log = Logger.getLogger(CollectionsBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @Inject
    private CollectionsBean collectionsBean;

    private Client httpClient;

    @Inject
    @DiscoverService("smartarticle-articles")
    private Provider<Optional<String>> articleBaseProvider;

    @PostConstruct
    private void init() { httpClient = ClientBuilder.newClient();}

    public List<Collection> getCollections(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();
        List<Collection> collections = JPAUtils.queryEntities(em, Collection.class, queryParameters);
        if (appProperties.isCollectionArticleServicesEnabled()) {
            for (Collection collection : collections) {
                try {
                    collection.setArticles(collectionsBean.getArticles(collection.getId()));
                } catch (InternalServerErrorException e) {
                    log.severe(e.getMessage());
                }
            }
        }
        return collections;
    }

    public List<Collection> getCollectionsFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Collection.class, queryParameters);
    }

    public Collection getCollection(Integer collectionId) {

        Collection collection = em.find(Collection.class, collectionId);

        if (collection == null) {
            appProperties.setHealthy(false);
            throw new NotFoundException();
        }

        collection.setArticles(collectionsBean.getArticles(collectionId));
        return collection;
    }


    public List<Article> getArticles(Integer collectiontId) {
        Optional<String> baseUrl = articleBaseProvider.get();
        if (baseUrl.isPresent()) {
            try {
                String link = baseUrl.get();
                return httpClient
                        .target(link + "/v1/articles?where=collectionId:EQ:" + collectiontId)
                        .request().get(new GenericType<List<Article>>() {
                        });
            } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                return null;
            }
        }
        return null;

    }

}
