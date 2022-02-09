package com.ftn.dostavaOSA.repositoryES;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dostavaOSA.dto.PorudzbinaDTO;

@Repository
public interface PorudzbinaRepositoryES extends ElasticsearchRepository<PorudzbinaDTO, Long>{

}
