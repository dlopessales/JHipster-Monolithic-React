package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Mestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MestreRepository extends JpaRepository<Mestre, Long> {

}
