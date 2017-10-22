package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("SELECT * FROM keluarga WHERE id = #{id_keluarga}")
	KeluargaModel selectKeluargaByNik(@Param("id_keluarga") int id_keluarga);
	
	@Select("SELECT * FROM keluarga WHERE nomor_kk = #{nomor_kk}")
	KeluargaModel selectKeluargaByNkk(@Param("nomor_kk") String nomor_kk);
	
	@Select("SELECT * FROM keluarga")
	List<KeluargaModel> selectAllKeluarga();
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void tambahKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET nomor_kk=#{nomor_kk}, alamat=#{alamat}, RT=#{rt}, RW=#{rw}, id_kelurahan=#{id_kelurahan}, is_tidak_berlaku=#{is_tidak_berlaku} WHERE id=#{id}")
	void ubahKeluarga(KeluargaModel keluarga);
}
