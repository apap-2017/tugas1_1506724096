package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.SidukDkiMapper;
import com.example.model.AlamatModel;
import com.example.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukDkiServiceDatabase implements SidukDkiService {
	@Autowired
	private SidukDkiMapper sidukMapper;
	
	@Override
	public AlamatModel selectAlamatByIdKelurahan(String id_kelurahan) {
		log.info("select data penduduk with id_kelurahan {}", id_kelurahan);
		return sidukMapper.selectAlamatByIdKelurahan(id_kelurahan);
	}
	
	@Override
	public List<PendudukModel> selectPendudukByIdKeluarga(int id_keluarga) {
		log.info("select all anggota keluarga", id_keluarga);
		return sidukMapper.selectPendudukByIdKeluarga(id_keluarga);
	}
}
