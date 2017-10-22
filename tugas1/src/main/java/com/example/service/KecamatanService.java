package com.example.service;

import java.util.List;
import com.example.model.KecamatanModel;

public interface KecamatanService {
	List<KecamatanModel> selectAllKecamatan();
	List<KecamatanModel> selectKecamatanByKota(int id_kota);
	KecamatanModel selectKecamatan(int id);
}
