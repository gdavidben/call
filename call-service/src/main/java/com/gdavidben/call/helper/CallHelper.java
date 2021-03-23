package com.gdavidben.call.helper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.gdavidben.call.entity.CallEntity;
import com.gdavidben.call.exception.RequestException;
import com.gdavidben.call.exception.ResourceNotFoundException;
import com.gdavidben.call.mapper.CallMapper;
import com.gdavidben.call.mapper.ValidationMapper;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Type;
import com.gdavidben.call.repository.CallRepository;

@ApplicationScoped
public class CallHelper {

	@Inject
	CallRepository callRepository;
	@Inject
	CallMapper callMapper;
	@Inject
	Validator validator;

	
	public void persist(CallEntity callEntity) {
		callRepository.persist(callEntity);
	}

	public void persist(List<CallEntity> callEntities) {
		callRepository.persist(callEntities);
	}
	
	public void delete(CallEntity callEntity) {
		callRepository.delete(callEntity);
	}

	public void delete(String id) throws ResourceNotFoundException {
		callRepository.delete(findEntity(id));
	}
	
	public CallEntity findEntity(String id) throws ResourceNotFoundException {
		CallEntity callEntity = callRepository.findById(callMapper.toObjectId(id));

		if (callEntity == null) {
			throw new ResourceNotFoundException("Call not found");
		}

		return callEntity;
	}

	public List<CallEntity> list() {
		return callRepository.findAll().list();
	}
	
	public List<CallEntity> list(Type type, Integer offset, Integer limit) {
		
		return callRepository.list(type, offset, limit);
	}
	
	public void validate(Call call) throws RequestException {
		Set<ConstraintViolation<Call>> violations = validator.validate(call);

		if (!violations.isEmpty()) {
			throw new RequestException("Invalid fields", violations.stream().map(ValidationMapper::toValidation).collect(Collectors.toList()));
		}
		
		if(call.getStart().isAfter(call.getEnd())) {
			throw new RequestException("Invalid period");
		}
	}

	public CallEntity beforeCreate(Call call) throws RequestException {
		validate(call);
		return callMapper.toCallEntity(call);
	}

	public CallEntity beforeCreateBulk(Call call) {
		try {
			return beforeCreate(call);
		} catch (RequestException e) {
			throw new RuntimeException(String.format("%s for [%s", e.getMessage(), call));
		}
	}
	
}
