package com.gdavidben.call.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.payload.Type;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ApplicationScoped
public class CallRepository implements PanacheMongoRepository<CallEntity> {

	public List<CallEntity> list(Type type, Integer offset, Integer limit) {
		PanacheQuery<CallEntity> panacheQuery;

		if (type == null) {
			panacheQuery = findAll();
		} else {
			panacheQuery = find("{type : ?1}", type);
		}

		return panacheQuery.page(Page.of(offset, limit)).list();
	}

}