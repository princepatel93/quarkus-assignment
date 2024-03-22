package org.walnutai.app.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.walnutai.app.DTO.NameDTO;
import org.walnutai.app.model.Name;
import org.walnutai.app.repository.NameRepository;


@Path("/")
public class NameResource {
    @Inject
    NameRepository nameRepository;

    @POST
    @Transactional
    public Response saveName(@Valid NameDTO nameDTO) {
        Name nameEntity = new Name();
        nameEntity.setName(nameDTO.getName());
        nameRepository.persist(nameEntity);
        return Response.status(Response.Status.CREATED).entity(nameEntity).build();
    }
}
