package com.example.service;

import java.util.List;
import com.example.model.KelurahanModel;

public interface KelurahanService {
	List<KelurahanModel> selectAllKelurahan();
	List<KelurahanModel> selectKelurahanByKecamatan(int id_kecamatan);
	KelurahanModel selectKelurahan(int id);
}
