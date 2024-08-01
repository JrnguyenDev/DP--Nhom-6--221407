package persistence;

import java.util.List;

import domain.model.HangHoa;

public class HangHoaDAOImpl implements HangHoaDAO {
    private HangHoaGateway hangHoaGateway;

    public HangHoaDAOImpl() {
        this.hangHoaGateway = new HangHoaJBDCGatewayImpl();
    }

    @Override
    public void add(HangHoa hangHoa) {
        hangHoaGateway.add(hangHoa);
    }

    @Override
    public List<HangHoa> getAll(String hangHoaCheck) {
        return hangHoaGateway.getAll(hangHoaCheck);
    }

    @Override
    public void delete(String hangHoaCheck, String maHang) {
        hangHoaGateway.delete(hangHoaCheck, maHang);
    }

    @Override
    public void update(HangHoa hangHoa, String maHang) {
        hangHoaGateway.update(hangHoa, maHang);
    }

    @Override
    public int tongSoLuong(String hangHoaCheck) {
        return hangHoaGateway.tongSoLuong(hangHoaCheck);
    }

}
