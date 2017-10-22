package com.example.service;

import java.util.List;
import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectKependudukan(String nik);
	List<PendudukModel> selectAllPenduduk();
	void tambahPenduduk(PendudukModel penduduk);
	void ubahPenduduk(PendudukModel penduduk);
	List<PendudukModel> selectPendudukByKelurahan(int id_kelurahan);
}
