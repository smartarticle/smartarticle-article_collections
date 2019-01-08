package si.fri.rso.smartarticle.collections.api.v1.resources;

import si.fri.rso.smartarticle.collections.models.entities.Collection;
import si.fri.rso.smartarticle.collections.services.beans.CollectionsBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@RequestScoped
@Path("/collections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CollectionsResource {

    @Inject
    private CollectionsBean collectionsBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getCollections() {

        List<Collection> collections = collectionsBean.getCollections(uriInfo);

        return Response.ok(collections).build();
    }

    @GET
    @Path("/filtered")
    public Response getCollectionsFiltered() {

        List<Collection> collections;

        collections = collectionsBean.getCollectionsFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(collections).build();
    }

    @GET
    @Path("/{collectionId}")
    public Response getCollection(@PathParam("collectionId") Integer collectionId) {

        Collection collection = collectionsBean.getCollection(collectionId);

        if (collection == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(collection).build();
    }
}
