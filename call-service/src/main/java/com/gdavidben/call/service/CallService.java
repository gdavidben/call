package com.gdavidben.call.service;

import java.util.List;

import com.gdavidben.call.exception.RequestException;
import com.gdavidben.call.exception.ResourceNotFoundException;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

public interface CallService {

	Call create(Call call) throws RequestException;
	
	List<Call> createBulk(List<Call> calls);

	void delete(String id) throws ResourceNotFoundException;

	Call find(String id) throws ResourceNotFoundException;

	List<Call> list(Type type, Integer offset, Integer limit);
	
	List<Statistic> statistics();
	
}
