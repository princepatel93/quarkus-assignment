package org.walnutai.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.walnutai.app.model.Name;

@ApplicationScoped
public class NameRepository implements PanacheRepository<Name> {
}