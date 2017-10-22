package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService{
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel selectKependudukan(String nik) {
		log.info("select data penduduk with nik {}", nik);
		return pendudukMapper.selectKependudukan(nik);
	}
	
	@Override
	public void tambahPenduduk(PendudukModel penduduk) {
		pendudukMapper.tambahPenduduk(penduduk);
	}
	
	@Override
	public List<PendudukModel> selectAllPenduduk() {
		log.info("select all id penduduk");
		return pendudukMapper.selectAllPenduduk();
	}
	
	@Override
	public void ubahPenduduk(PendudukModel penduduk) {
		log.info("penduduk {} name updated to {}", penduduk.getNama(), penduduk.getNik());
		pendudukMapper.ubahPenduduk(penduduk);
	}
	
	@Override
	public List<PendudukModel> selectPendudukByKelurahan(int id_kelurahan) {
		log.info("select all id keluarga");
		return pendudukMapper.selectPendudukByKelurahan(id_kelurahan);
	}
}
