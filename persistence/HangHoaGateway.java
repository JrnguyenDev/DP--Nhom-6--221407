package persistence;

import java.util.List;

import domain.model.HangHoa;

public interface HangHoaGateway {
   void add(HangHoa hangHoa); 
   void delete(String hangHoaCheck, String maHang);
   void update(HangHoa hangHoa, String maHang);
   int tongSoLuong(String hangHoaCheck);
   List<HangHoa> getAll(String hangHoaCheck);
}
