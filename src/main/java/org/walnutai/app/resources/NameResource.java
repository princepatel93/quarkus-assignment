package org.walnutai.app.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.walnutai.app.DTO.NameDTO;
import org.walnutai.app.model.Name;
import org.walnutai.app.repository.NameRepository;

import java.util.List;


@Path("/")
public class NameResource {
    @Inject
    NameRepository nameRepository;

    @POST
    @Path("/name")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveName(@Valid NameDTO nameDTO) {
        Name nameEntity = new Name();
        nameEntity.setName(nameDTO.getName());
        nameRepository.persist(nameEntity);
        return Response.status(Response.Status.CREATED).entity(nameEntity).build();
    }

    @GET
    @Path("/list")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listNames(@QueryParam("sort") String sort,
                              @QueryParam("filter") String filter,
                              @QueryParam("page") int page) {

        List<Name> resultNames;

        // Apply filtering if filter parameter is provided
        if (filter != null) {
            String[] filterParts = filter.split(":");
            String filterColumn = filterParts[0];
            String filterValue = filterParts[1];
            resultNames = nameRepository.find("FROM Name WHERE " + filterColumn + " = ?1", filterValue).list();
        } else {
            resultNames = nameRepository.listAll();
        }

        // Apply sorting if sort parameter is provided
        if (sort != null) {
            String[] sortParts = sort.split(":");
            String sortColumn = sortParts[0];
            String sortDirection = sortParts[1];
            resultNames = applySort(resultNames, sortColumn, sortDirection);
        }

        // Apply pagination
        int pageSize = 10; // Default page size
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, resultNames.size());
        List<Name> paginatedList = resultNames.subList(start, end);

        return Response.ok(paginatedList).build();
    }

    // Helper method to apply sorting based on the sort parameter
    private List<Name> applySort(List<Name> list, String sortColumn, String sortDirection) {
        // Since database contains only one column, implementing sorting for one column
        if ("name".equalsIgnoreCase(sortColumn)) {
            list.sort((n1, n2) -> sortDirection.equalsIgnoreCase("asc") ?
                    n1.getName().compareTo(n2.getName()) :
                    n2.getName().compareTo(n1.getName()));
        }
        // Add more sorting options if needed
        return list;
    }
}

