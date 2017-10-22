package com.example.service;

import java.util.List;
import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluargaByNik(int id_keluarga);
	KeluargaModel selectKeluargaByNkk(String nomor_kk);
	List<KeluargaModel> selectAllKeluarga();
	void tambahKeluarga(KeluargaModel keluarga);
	void ubahKeluarga(KeluargaModel keluarga);
}
