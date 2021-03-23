package com.gdavidben.call.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.exception.RequestException;
import com.gdavidben.call.exception.ResourceNotFoundException;
import com.gdavidben.call.helper.CallHelper;
import com.gdavidben.call.helper.StatisticHelper;
import com.gdavidben.call.mapper.CallMapper;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;
import com.gdavidben.call.service.CallService;

@ApplicationScoped
public class CallServiceImpl implements CallService {

	@Inject
	CallHelper callHelper;
	@Inject
	StatisticHelper statisticHelper;
	@Inject
	CallMapper callMapper;

	public Call create(Call call) throws RequestException {
		CallEntity callEntity = callHelper.beforeCreate(call);
		callHelper.persist(callEntity);

		return callMapper.toCall(callEntity);
	}

	public List<Call> createBulk(List<Call> calls) {

		List<CallEntity> callEntities = calls.stream().map(callHelper::beforeCreateBulk).collect(Collectors.toList());

		callHelper.persist(callEntities);

		return callEntities.stream().map(callMapper::toCall).collect(Collectors.toList());
	}

	public void delete(String id) throws ResourceNotFoundException {
		callHelper.delete(id);
	}

	public Call find(String id) throws ResourceNotFoundException {
		return callMapper.toCall(callHelper.findEntity(id));
	}

	public List<Call> list(Type type, Integer offset, Integer limit) {
		return callHelper.list(type, offset, limit).stream().map(callMapper::toCall).collect(Collectors.toList());
	}

	public List<Statistic> statistics() {
		return statisticHelper.generateStatistics(callHelper.list());
	}

}
