package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.model.HangDienMay;
import domain.model.HangHoa;
import domain.model.HangSanhSu;
import domain.model.HangThucPham;

import java.sql.Date;

public class HangHoaJBDCGatewayImpl implements HangHoaGateway {
    private DatabaseConnection dbConnection;

    public HangHoaJBDCGatewayImpl() {
        this.dbConnection = new DatabaseConnection();
    }

    @Override
    public void add(HangHoa hangHoa) {
        String sql = "INSERT INTO HangHoa (MaHang, TenHang, SoLuongTon, DonGia) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, hangHoa.getMaHang());
            statement.setString(2, hangHoa.getTenHang());
            statement.setInt(3, hangHoa.getSoLuongTon());
            statement.setDouble(4, hangHoa.getDonGia());
            
            statement.executeUpdate();

            if (hangHoa instanceof HangThucPham) {
                HangThucPham htp = (HangThucPham) hangHoa;
                String thucPhamSql = "INSERT INTO HangThucPham (MaHang, NgaySanXuat, NgayHetHan, NhaCungCap) VALUES (?, ?, ?, ?)";
                try (PreparedStatement thucPhamStmt = connection.prepareStatement(thucPhamSql)) {
                    thucPhamStmt.setString(1, htp.getMaHang());
                    thucPhamStmt.setDate(2, Date.valueOf(htp.getNgaySanXuat()));
                    thucPhamStmt.setDate(3, Date.valueOf(htp.getNgayHetHan()));
                    thucPhamStmt.setString(4, htp.getNhaCungCap());
                    thucPhamStmt.executeUpdate();
                }
            } else if (hangHoa instanceof HangDienMay) {
                HangDienMay hdm = (HangDienMay) hangHoa;
                String dienMaySql = "INSERT INTO HangDienMay (MaHang, ThoiGianBaoHanh, CongSuat) VALUES (?, ?, ?)";
                try (PreparedStatement dienMayStmt = connection.prepareStatement(dienMaySql)) {
                    dienMayStmt.setString(1, hdm.getMaHang());
                    dienMayStmt.setInt(2, hdm.getThoiGianBaoHanh());
                    dienMayStmt.setDouble(3, hdm.getCongSuat());
                    dienMayStmt.executeUpdate();
                }
            } else if (hangHoa instanceof HangSanhSu) {
                HangSanhSu hss = (HangSanhSu) hangHoa;
                String sanhSuSql = "INSERT INTO HangSanhSu (MaHang, NhaSanXuat, NgayNhapKho) VALUES (?, ?, ?)";
                try (PreparedStatement sanhSuStmt = connection.prepareStatement(sanhSuSql)) {
                    sanhSuStmt.setString(1, hss.getMaHang());
                    sanhSuStmt.setString(2, hss.getNhaSanXuat());
                    sanhSuStmt.setDate(3, Date.valueOf(hss.getNgayNhapKho()));
                    sanhSuStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HangHoa> getAll(String hangHoaCheck) {
        List<HangHoa> hangHoas = new ArrayList<>();
        String sql = "";
        if (hangHoaCheck.equals("HangHoa")) {
            sql = "SELECT * FROM HangHoa";
        } else if (hangHoaCheck.equals("HangThucPham")) {
            sql = "SELECT hh.MaHang, hh.TenHang, hh.SoLuongTon, hh.DonGia, tp.NgaySanXuat, tp.NgayHetHan, tp.NhaCungCap " +
        "FROM HangHoa hh JOIN HangThucPham tp ON hh.MaHang = tp.MaHang";
        } else if (hangHoaCheck.equals("HangDienMay")) {
            sql = "SELECT hh.MaHang, hh.TenHang, hh.SoLuongTon, hh.DonGia, dm.ThoiGianBaoHanh, dm.CongSuat " +
        "FROM HangHoa hh JOIN HangDienMay dm ON hh.MaHang = dm.MaHang";
        } else if (hangHoaCheck.equals("HangSanhSu")) {
            sql =  "SELECT hh.MaHang, hh.TenHang, hh.SoLuongTon, hh.DonGia, ss.NhaSanXuat, ss.NgayNhapKho " +
            "FROM HangHoa hh JOIN HangSanhSu ss ON hh.MaHang = ss.MaHang";
        }

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String maHang = resultSet.getString("MaHang");
                String tenHang = resultSet.getString("TenHang");
                int soLuongTon = resultSet.getInt("SoLuongTon");
                double donGia = resultSet.getDouble("DonGia");

                if (hangHoaCheck.equals("HangHoa")){
                    hangHoas.add(new HangHoa(maHang, tenHang, donGia, soLuongTon));
                    
                } else if (hangHoaCheck.equals("HangThucPham")){
                    LocalDate ngaySanXuat = resultSet.getObject("NgaySanXuat", LocalDate.class);
                    LocalDate ngayHetHan = resultSet.getObject("NgayHetHan", LocalDate.class);
                    String nhaCungCap = resultSet.getString("NhaCungCap");
                    hangHoas.add(new HangThucPham(maHang, tenHang, donGia, soLuongTon, ngaySanXuat, ngayHetHan, nhaCungCap));
                } else if (hangHoaCheck.equals("HangDienMay")){
                    int thoiGianBaoHanh = resultSet.getInt("ThoiGianBaoHanh");
                    double congSuat = resultSet.getDouble("CongSuat");
                    hangHoas.add(new HangDienMay(maHang, tenHang, donGia, soLuongTon,  congSuat, thoiGianBaoHanh));
                } else if (hangHoaCheck.equals("HangSanhSu")){
                    String nhaSanXuat = resultSet.getString("NhaSanXuat");
                    LocalDate ngayNhapKho = resultSet.getObject("NgayNhapKho", LocalDate.class);
                    hangHoas.add(new HangSanhSu(maHang, tenHang, donGia, soLuongTon, nhaSanXuat, ngayNhapKho));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hangHoas;
        
    }

    @Override
    public void delete(String hangHoaCheck, String maHang) {
        String sql = "DELETE FROM HangHoa WHERE MaHang = ?";
        String sql2 = "DELETE FROM HangThucPham WHERE MaHang = ?";
        String sql3 = "DELETE FROM HangDienMay WHERE MaHang = ?";
        String sql4 = "DELETE FROM HangSanhSu WHERE MaHang = ?";

        if (hangHoaCheck.equals("HangThucPham")) {
            try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql2);
            PreparedStatement statementHangHoa = connection.prepareStatement(sql);
            ) {
                statement.setString(1, maHang);
                statement.executeUpdate();
                statementHangHoa.setString(1, maHang);
                statementHangHoa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (hangHoaCheck.equals("HangDienMay")){
            try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql3);
            PreparedStatement statementHangHoa = connection.prepareStatement(sql);
            ) {
                statement.setString(1, maHang);
                statement.executeUpdate();
                statementHangHoa.setString(1, maHang);
                statementHangHoa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (hangHoaCheck.equals("HangSanhSu")){
            try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql4);
            PreparedStatement statementHangHoa = connection.prepareStatement(sql);
            ) {
                statement.setString(1, maHang);
                statement.executeUpdate();
                statementHangHoa.setString(1, maHang);
                statementHangHoa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.setString(1, maHang);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(HangHoa hangHoa, String maHang) {
        String sqlHangHoa = "UPDATE HangHoa SET TenHang = ?, SoLuongTon = ?, DonGia = ? WHERE MaHang = ?";
        String sql = "UPDATE HangDienMay SET ThoiGianBaoHanh = ?, CongSuat = ? WHERE MaHang = ?";
        String sql2 = "UPDATE HangThucPham SET NgaySanXuat = ?, NgayHetHan = ?, NhaCungCap = ? WHERE MaHang = ?";
        String sql3 = "UPDATE HangSanhSu SET NhaSanXuat = ?, NgayNhapKho = ? WHERE MaHang = ?";
        if (hangHoa instanceof HangDienMay) {
            HangDienMay hdm = (HangDienMay) hangHoa;
            try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statementHangHoa = connection.prepareStatement(sqlHangHoa);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statementHangHoa.setString(1, hdm.getTenHang());
            statementHangHoa.setInt(2, hdm.getSoLuongTon());
            statementHangHoa.setDouble(3, hdm.getDonGia());
            statementHangHoa.setString(4, maHang);
            statementHangHoa.executeUpdate();

            statement.setInt(1, hdm.getThoiGianBaoHanh());
            statement.setDouble(2, hdm.getCongSuat());
            statement.setString(3, maHang);
            statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (hangHoa instanceof HangThucPham){
            HangThucPham htp = (HangThucPham) hangHoa;
            try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementHangHoa = connection.prepareStatement(sqlHangHoa);
             PreparedStatement statement = connection.prepareStatement(sql2)) {
                statementHangHoa.setString(1, htp.getTenHang());
                statementHangHoa.setInt(2, htp.getSoLuongTon());
                statementHangHoa.setDouble(3, htp.getDonGia());
                statementHangHoa.setString(4, maHang);
                statementHangHoa.executeUpdate();

                statement.setDate(1, Date.valueOf(htp.getNgaySanXuat()));
                statement.setDate(2, Date.valueOf(htp.getNgayHetHan()));
                statement.setString(3, htp.getNhaCungCap());
                statement.setString(4, maHang);
                statement.executeUpdate();
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (hangHoa instanceof HangSanhSu){
            HangSanhSu hss = (HangSanhSu) hangHoa;
            try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementHangHoa = connection.prepareStatement(sqlHangHoa);
             PreparedStatement statement = connection.prepareStatement(sql3)) {
                statementHangHoa.setString(1, hss.getTenHang());
                statementHangHoa.setInt(2, hss.getSoLuongTon());
                statementHangHoa.setDouble(3, hss.getDonGia());
                statementHangHoa.setString(4, maHang);
                statementHangHoa.executeUpdate();

                statement.setString(1, hss.getNhaSanXuat());
                statement.setDate(2, Date.valueOf(hss.getNgayNhapKho()));
                statement.setString(3, maHang);
                statement.executeUpdate();
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementHangHoa = connection.prepareStatement(sqlHangHoa)) {
                statementHangHoa.setString(1, hangHoa.getTenHang());
                statementHangHoa.setInt(2, hangHoa.getSoLuongTon());
                statementHangHoa.setDouble(3, hangHoa.getDonGia());
                statementHangHoa.setString(4, maHang);
                statementHangHoa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }    
    }

    @Override
    public int tongSoLuong(String hangHoaCheck) {
        String sql = "";
        int tongSoLuong = 0;
        if (hangHoaCheck.equals("HangThucPham")) {
           sql = "SELECT SUM(SoLuongTon) AS TotalSoLuongTon FROM HangHoa hh JOIN HangThucPham htp ON hh.MaHang = htp.MaHang";
        } else if (hangHoaCheck.equals("HangDienMay")) {
            sql = "SELECT SUM(SoLuongTon) FROM HangHoa hh JOIN HangDienMay hdm ON hh.MaHang = hdm.MaHang";
        } else if (hangHoaCheck.equals("HangSanhSu")) {
            sql = "SELECT SUM(SoLuongTon) FROM HangHoa hh JOIN HangSanhSu hss ON hh.MaHang = hss.MaHang";
        } else {
            sql = "SELECT SUM(SoLuongTon) FROM HangHoa";
        }
        try (Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            tongSoLuong = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }  
        return tongSoLuong;
        
    }
}
