package com.example.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.model.AlamatModel;
import com.example.model.PendudukModel;

@Mapper
public interface SidukDkiMapper {
	@Select("SELECT * FROM kelurahan as kr LEFT OUTER JOIN kecamatan as kc ON kr.id_kecamatan=kc.id LEFT OUTER JOIN kota as kt ON kc.id_kota = kt.id WHERE kr.id = #{id_kelurahan}")
	AlamatModel selectAlamatByIdKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("SELECT * FROM penduduk WHERE id_keluarga = #{id_keluarga}")
	List<PendudukModel> selectPendudukByIdKeluarga(@Param("id_keluarga") int id_keluarga);
}
