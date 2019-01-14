package si.fri.rso.smartarticle.collections.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.smartarticle.collections.models.entities.Collection;
import si.fri.rso.smartarticle.collections.services.beans.CollectionsBean;
import si.fri.rso.smartarticle.collections.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Log
@ApplicationScoped
@Path("/collections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CollectionsResource {

    @Inject
    private CollectionsBean collectionsBean;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private AppProperties appProperties;

    @GET
    public Response getCollections() {
        if (appProperties.isCollectionServicesEnabled()) {
            List<Collection> collections = collectionsBean.getCollections(uriInfo);
            appProperties.setHealthy(true);
            return Response.ok(collections).build();
        }
        appProperties.setHealthy(true);
        return Response.ok().build();
    }

    @GET
    @Path("/filtered")
    public Response getCollectionsFiltered() {
        if (appProperties.isCollectionServicesEnabled()) {
            List<Collection> collections;

            collections = collectionsBean.getCollectionsFilter(uriInfo);
            appProperties.setHealthy(true);
            return Response.status(Response.Status.OK).entity(collections).build();
        }
        appProperties.setHealthy(true);
        return Response.ok().build();
    }

    @GET
    @Path("/{collectionId}")
    public Response getCollection(@PathParam("collectionId") Integer collectionId) {
        if (appProperties.isCollectionServicesEnabled()) {
            Collection collection = collectionsBean.getCollection(collectionId);

            if (collection == null) {
                appProperties.setHealthy(true);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            appProperties.setHealthy(true);
            return Response.status(Response.Status.OK).entity(collection).build();
        }
        appProperties.setHealthy(true);
        return Response.ok().build();
    }
}
