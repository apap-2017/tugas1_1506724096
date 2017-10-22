package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("SELECT * FROM kelurahan")
	List<KelurahanModel> selectAllKelurahan();
	
	@Select("SELECT * FROM kelurahan WHERE id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanByKecamatan(int id_kecamatan);
	
	@Select("SELECT * FROM kelurahan WHERE  id = #{id}")
	KelurahanModel selectKelurahan(@Param("id") int id);
}
