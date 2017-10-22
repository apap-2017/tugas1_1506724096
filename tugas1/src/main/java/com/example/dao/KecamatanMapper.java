package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("SELECT * FROM kecamatan")
	List<KecamatanModel> selectAllKecamatan();
	
	@Select("SELECT * FROM kecamatan WHERE id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatanByKota(int id_kota);
	
	@Select("SELECT * FROM kecamatan WHERE id = #{id}")
	KecamatanModel selectKecamatan(@Param("id") int id);
}
