package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluargaByNik(int id_keluarga) {
		log.info("select data penduduk with id_keluarga {}", id_keluarga);
		return keluargaMapper.selectKeluargaByNik(id_keluarga);
	}
	
	@Override
	public KeluargaModel selectKeluargaByNkk(String nomor_kk) {
		log.info("select data keluarga with nomor_kk {}", nomor_kk);
		return keluargaMapper.selectKeluargaByNkk(nomor_kk);
	}
	
	@Override
	public List<KeluargaModel> selectAllKeluarga() {
		log.info("select all Keluarga");
		return keluargaMapper.selectAllKeluarga();
	}
	
	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		keluargaMapper.tambahKeluarga(keluarga);
	}
	
	@Override
	public void ubahKeluarga(KeluargaModel keluarga) {
		log.info("keluarga {} alamat updated to {}", keluarga.getAlamat(), keluarga.getId_kelurahan());
		keluargaMapper.ubahKeluarga(keluarga);
	}
}
