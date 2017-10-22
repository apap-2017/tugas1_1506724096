package com.example.service;


import java.util.List;
import com.example.model.AlamatModel;
import com.example.model.PendudukModel;

public interface SidukDkiService {
	AlamatModel selectAlamatByIdKelurahan(String id_kelurahan);
	List<PendudukModel> selectPendudukByIdKeluarga(int id_keluarga);
}
