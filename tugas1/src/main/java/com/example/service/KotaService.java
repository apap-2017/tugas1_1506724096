package com.example.service;

import java.util.List;
import com.example.model.KotaModel;

public interface KotaService {
	List<KotaModel> selectAllKota();
	KotaModel selectKota(int id);
}
