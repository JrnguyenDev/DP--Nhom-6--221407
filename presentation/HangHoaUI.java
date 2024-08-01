package presentation;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import domain.model.*;
import domain.service.HangHoaService;
import domain.service.HangHoaServiceImpl;
import persistence.HangHoaDAOImpl;
import domain.observer.Subcriber;


public class HangHoaUI extends JFrame implements Subcriber {

    private JTextField maHangField, tenHangField, soLuongTonField, donGiaField;
    private JTextField ngaySanXuatField, ngayHetHanField, nhaCungCapField;
    private JTextField thoiGianBaoHanhField, congSuatField;
    private JTextField nhaSanXuatField, ngayNhapKhoField;
    private JLabel nhaCungCapLabel, ngaySanXuatLabel, ngayHetHanLabel;
    private JLabel thoiGianBaoHanhLabel, congSuatLabel;
    private JLabel nhaSanXuatLabel, ngayNhapKhoLabel;
    private JComboBox<String> loaiHangComboBox;
    private JPanel chiTietPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private HangHoaController controller;

    private HangHoaService hangHoaService;
    public HangHoaUI(HangHoaService hangHoaService, HangHoa hangHoa) {
        this.hangHoaService = hangHoaService;
        controller = new HangHoaController(this, hangHoa);
        initUI();
    }

    private void initUI() {
        setTitle("Quản Lý Kho Siêu Thị");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("Mã Hàng:"), c);
        c.gridx = 1;
        maHangField = new JTextField(15);
        formPanel.add(maHangField, c);

        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(new JLabel("Tên Hàng:"), c);
        c.gridx = 1;
        tenHangField = new JTextField(15);
        formPanel.add(tenHangField, c);

        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Số Lượng Tồn:"), c);
        c.gridx = 1;
        soLuongTonField = new JTextField(15);
        formPanel.add(soLuongTonField, c);

        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(new JLabel("Đơn Giá:"), c);
        c.gridx = 1;
        donGiaField = new JTextField(15);
        formPanel.add(donGiaField, c);

        c.gridx = 0;
        c.gridy = 4;
        formPanel.add(new JLabel("Loại Hàng:"), c);
        c.gridx = 1;
        loaiHangComboBox = new JComboBox<>(new String[]{"HangThucPham", "HangDienMay", "HangSanhSu", "HangHoa"});
        formPanel.add(loaiHangComboBox, c);

        // Panel chi tiết
        chiTietPanel = new JPanel(new CardLayout());

        // Chi tiết hàng thực phẩm
        JPanel thucPhamPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        ngaySanXuatLabel = new JLabel("Ngày Sản Xuất:");
        thucPhamPanel.add(ngaySanXuatLabel, c);
        c.gridx = 1;
        ngaySanXuatField = new JTextField(15);
        thucPhamPanel.add(ngaySanXuatField, c);

        c.gridx = 0;
        c.gridy = 1;
        ngayHetHanLabel = new JLabel("Ngày Hết Hạn:");
        thucPhamPanel.add(ngayHetHanLabel, c);
        c.gridx = 1;
        ngayHetHanField = new JTextField(15);
        thucPhamPanel.add(ngayHetHanField, c);

        c.gridx = 0;
        c.gridy = 2;
        nhaCungCapLabel = new JLabel("Nhà Cung Cấp:");
        thucPhamPanel.add(nhaCungCapLabel, c);
        c.gridx = 1;
        nhaCungCapField = new JTextField(15);
        thucPhamPanel.add(nhaCungCapField, c);

        chiTietPanel.add(thucPhamPanel, "HangThucPham");

        // Chi tiết hàng điện máy
        JPanel dienMayPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        thoiGianBaoHanhLabel = new JLabel("Thời Gian Bảo Hành (tháng):");
        dienMayPanel.add(thoiGianBaoHanhLabel, c);
        c.gridx = 1;
        thoiGianBaoHanhField = new JTextField(15);
        dienMayPanel.add(thoiGianBaoHanhField, c);

        c.gridx = 0;
        c.gridy = 1;
        congSuatLabel = new JLabel("Công Suất (KW):");
        dienMayPanel.add(congSuatLabel, c);
        c.gridx = 1;
        congSuatField = new JTextField(15);
        dienMayPanel.add(congSuatField, c);

        chiTietPanel.add(dienMayPanel, "HangDienMay");

        // Chi tiết hàng sành sứ
        JPanel sanhSuPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        nhaSanXuatLabel = new JLabel("Nhà Sản Xuất:");
        sanhSuPanel.add(nhaSanXuatLabel, c);
        c.gridx = 1;
        nhaSanXuatField = new JTextField(15);
        sanhSuPanel.add(nhaSanXuatField, c);

        c.gridx = 0;
        c.gridy = 1;
        ngayNhapKhoLabel = new JLabel("Ngày Nhập Kho:");
        sanhSuPanel.add(ngayNhapKhoLabel, c);
        c.gridx = 1;
        ngayNhapKhoField = new JTextField(15);
        sanhSuPanel.add(ngayNhapKhoField, c);

        chiTietPanel.add(sanhSuPanel, "HangSanhSu");

        // Tạo panel chứa form nhập liệu và chi tiết hàng hóa
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.NORTH);
        inputPanel.add(chiTietPanel, BorderLayout.CENTER);

        // Bảng hiển thị danh sách hàng hóa
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Sử dụng JSplitPane để chia giao diện thành hai phần
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, tableScrollPane);
        splitPane.setDividerLocation(300); // Chia theo tỷ lệ 300 pixel cho inputPanel

        // Thêm JSplitPane vào JFrame
        add(splitPane);

        // Hiển thị chi tiết tương ứng khi chọn loại hàng
        loaiHangComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) chiTietPanel.getLayout();
                String selectedLoaiHang = (String) loaiHangComboBox.getSelectedItem();
                cardLayout.show(chiTietPanel, selectedLoaiHang);
                updateTableModel(selectedLoaiHang);
                loadTableData(selectedLoaiHang);
                updateFormVisibility(selectedLoaiHang); // Update visibility of text fields
            }
        });

        // Add Buttons
        JButton addButton = new JButton("Thêm hàng hóa");
        JButton deleteButton = new JButton("Xóa hàng hóa");
        JButton updateButton = new JButton("Sửa hàng hóa");
        JButton tongSoLuongTonButton = new JButton("Tổng số lượng tồn");
        // Set action commands
        
        addButton.setActionCommand("Thêm hàng hóa");
        deleteButton.setActionCommand("Xóa hàng hóa");
        updateButton.setActionCommand("Sửa hàng hóa");
        tongSoLuongTonButton.setActionCommand("Tổng số lượng tồn");
        // Add action listeners
        addButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        updateButton.addActionListener(controller);
        tongSoLuongTonButton.addActionListener(controller);
        // Add radio buttons for example

        // Add buttons and radio buttons to form
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(tongSoLuongTonButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        updateTableModel("HangThucPham"); // Load default data model
        loadTableData("HangThucPham"); // Load default data

        // Lắng nghe sự kiện click lên table để cập nhật vào text fields
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                updateTextFieldsFromTable(selectedRow);
            }
        });
    }

    

    public void addHangHoa(){
        String maHang = getMaHangField().getText();
        String tenHang = getTenHangField().getText();
        int soLuongTon = Integer.parseInt(getSoLuongTonField().getText());
        double donGia = Double.parseDouble(getDonGiaField().getText());
        String loaiHang = (String) getLoaiHangComboBox().getSelectedItem();

        if ("HangThucPham".equals(loaiHang)) {
            LocalDate ngaySanXuat = LocalDate.parse(getNgaySanXuatField().getText());
            LocalDate ngayHetHan = LocalDate.parse(getNgayHetHanField().getText());
            String nhaCungCap = getNhaCungCapField().getText();
            HangThucPham hangThucPham = new HangThucPham(maHang, tenHang, donGia, soLuongTon, ngaySanXuat, ngayHetHan, nhaCungCap);
            hangHoaService.add(hangThucPham);
        } else if ("HangDienMay".equals(loaiHang)) {
            int thoiGianBaoHanh = Integer.parseInt(getThoiGianBaoHanhField().getText());
            double congSuat = Double.parseDouble(getCongSuatField().getText());
            HangDienMay hangDienMay = new HangDienMay(maHang, tenHang, donGia, soLuongTon, congSuat, thoiGianBaoHanh);
            hangHoaService.add(hangDienMay);
        } else if ("HangSanhSu".equals(loaiHang)) {
            String nhaSanXuat = getNhaSanXuatField().getText();
            LocalDate ngayNhapKho = LocalDate.parse(getNgayNhapKhoField().getText());
            HangSanhSu hangSanhSu = new HangSanhSu(maHang, tenHang, donGia, soLuongTon, nhaSanXuat, ngayNhapKho);
            hangHoaService.add(hangSanhSu);
        } else {
            HangHoa hangHoa = new HangHoa(maHang, tenHang, donGia, soLuongTon);
            hangHoaService.add(hangHoa);
        }
        loadTableData(loaiHang);
        clearFormFields();
    }

    public void deleteHangHoa(HangHoa hangHoa) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maHang = getMaHangField().getText();
            String loaiHang = (String) getLoaiHangComboBox().getSelectedItem();
            if ("HangThucPham".equals(loaiHang)) {
                hangHoaService.delete("HangThucPham",maHang);
            } else if ("HangDienMay".equals(loaiHang)) {
                hangHoaService.delete("HangDienMay",maHang);
            } else if ("HangSanhSu".equals(loaiHang)) {
                hangHoaService.delete("HangSanhSu",maHang);
            } else {
                hangHoaService.delete("",maHang);
            }       
            loadTableData(loaiHang);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng hóa để xóa.");
        }
    }

    public void updateHangHoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maHang = (String) tableModel.getValueAt(selectedRow, 0);
            String tenHang = tenHangField.getText();
            int soLuongTon = Integer.parseInt(soLuongTonField.getText());
            double donGia = Double.parseDouble(donGiaField.getText());
            String loaiHang = (String) loaiHangComboBox.getSelectedItem();

            if ("HangThucPham".equals(loaiHang)) {
                LocalDate ngaySanXuat = LocalDate.parse(ngaySanXuatField.getText());
                LocalDate ngayHetHan = LocalDate.parse(ngayHetHanField.getText());
                String nhaCungCap = nhaCungCapField.getText();
                HangThucPham htp = new HangThucPham(maHang, tenHang, donGia, soLuongTon, ngaySanXuat, ngayHetHan, nhaCungCap);
                hangHoaService.update(htp, maHang);
            } else if ("HangDienMay".equals(loaiHang)) {
                int thoiGianBaoHanh = Integer.parseInt(thoiGianBaoHanhField.getText());
                double congSuat = Double.parseDouble(congSuatField.getText());
                HangDienMay updatedHangDienMay = new HangDienMay(maHang, tenHang, donGia, soLuongTon, congSuat, thoiGianBaoHanh);
                hangHoaService.update(updatedHangDienMay, maHang);
            } else if ("HangSanhSu".equals(loaiHang)) {
                String nhaSanXuat = nhaSanXuatField.getText();
                LocalDate ngayNhapKho = LocalDate.parse(ngayNhapKhoField.getText());
                HangSanhSu updatedHangSanhSu = new HangSanhSu(maHang, tenHang, donGia, soLuongTon, nhaSanXuat, ngayNhapKho);
                hangHoaService.update(updatedHangSanhSu, maHang);
            }

            loadTableData(loaiHang);
            clearFormFields();
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hàng hóa để sửa.");
        }
    }

    public void tongSoLuongTon() {
        String loaiHang = (String) loaiHangComboBox.getSelectedItem();
        if ("HangThucPham".equals(loaiHang)) {
            JOptionPane.showMessageDialog(null, "Tổng số lượng tồn hàng thực phẩm: " + hangHoaService.tongSoLuong("HangThucPham"));
        } else if ("HangDienMay".equals(loaiHang)) {
            JOptionPane.showMessageDialog(null, "Tổng số lượng tồn hàng điện máy: " + hangHoaService.tongSoLuong("HangDienMay"));
        } else if ("HangSanhSu".equals(loaiHang)) {
            JOptionPane.showMessageDialog(null, "Tổng số lượng tồn hàng sành sứ: " + hangHoaService.tongSoLuong("HangSanhSu"));
        } else {
            JOptionPane.showMessageDialog(null, "Tổng số lượng tồn tất cả hàng hóa: " + hangHoaService.tongSoLuong("HangHoa"));           
        }
    }

    public void kTraTGHH() {
       
    }

    private void updateTableModel(String loaiHang) {
        tableModel.setRowCount(0); // Clear existing data
        switch (loaiHang) {
            case "HangThucPham":
                tableModel.setColumnIdentifiers(new Object[]{"Mã Hàng", "Tên Hàng", "Số Lượng Tồn", "Đơn Giá", "Ngày Sản Xuất", "Ngày Hết Hạn", "Nhà Cung Cấp"});
                break;
            case "HangDienMay":
                tableModel.setColumnIdentifiers(new Object[]{"Mã Hàng", "Tên Hàng", "Số Lượng Tồn", "Đơn Giá", "Thời Gian Bảo Hành", "Công Suất"});
                break;
            case "HangSanhSu":
                tableModel.setColumnIdentifiers(new Object[]{"Mã Hàng", "Tên Hàng", "Số Lượng Tồn", "Đơn Giá", "Nhà Sản Xuất", "Ngày Nhập Kho"});
                break;
            default:
                tableModel.setColumnIdentifiers(new Object[]{"Mã Hàng", "Tên Hàng", "Số Lượng Tồn", "Đơn Giá"});
                break;
        }
    }

    public void loadTableData(String loaiHang) {
        tableModel.setRowCount(0); // Clear existing data
        List<HangHoa> hangHoas = null;
        switch (loaiHang) {
            case "HangThucPham":
                hangHoas = hangHoaService.getAll("HangThucPham");
                break;
            case "HangDienMay":
                hangHoas = hangHoaService.getAll("HangDienMay");
                break;
            case "HangSanhSu":
                hangHoas = hangHoaService.getAll("HangSanhSu");
                break;
            default:
                hangHoas = hangHoaService.getAll("HangHoa");
                break;
        }
        for (HangHoa hangHoa : hangHoas) {
            if (hangHoa instanceof HangThucPham) {
                HangThucPham ht = (HangThucPham) hangHoa;
                tableModel.addRow(new Object[]{ht.getMaHang(), ht.getTenHang(), ht.getSoLuongTon(), ht.getDonGia(), ht.getNgaySanXuat(), ht.getNgayHetHan(), ht.getNhaCungCap()});
            } else if (hangHoa instanceof HangDienMay) {
                HangDienMay hd = (HangDienMay) hangHoa;
                tableModel.addRow(new Object[]{hd.getMaHang(), hd.getTenHang(), hd.getSoLuongTon(), hd.getDonGia(), hd.getThoiGianBaoHanh(), hd.getCongSuat()});
            } else if (hangHoa instanceof HangSanhSu) {
                HangSanhSu hs = (HangSanhSu) hangHoa;
                tableModel.addRow(new Object[]{hs.getMaHang(), hs.getTenHang(), hs.getSoLuongTon(), hs.getDonGia(), hs.getNhaSanXuat(), hs.getNgayNhapKho()});
            } else {
                tableModel.addRow(new Object[]{hangHoa.getMaHang(), hangHoa.getTenHang(), hangHoa.getSoLuongTon(), hangHoa.getDonGia()});
            }
        }
    }

    public void clearFormFields() {
        maHangField.setText("");
        tenHangField.setText("");
        soLuongTonField.setText("");
        donGiaField.setText("");
        ngaySanXuatField.setText("");
        ngayHetHanField.setText("");
        nhaCungCapField.setText("");
        thoiGianBaoHanhField.setText("");
        congSuatField.setText("");
        nhaSanXuatField.setText("");
        ngayNhapKhoField.setText("");
    }

    public void updateTextFieldsFromTable(int selectedRow) {
        maHangField.setText((String) tableModel.getValueAt(selectedRow, 0));
        tenHangField.setText((String) tableModel.getValueAt(selectedRow, 1));
        soLuongTonField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
        donGiaField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));

        String loaiHang = (String) loaiHangComboBox.getSelectedItem();

        if ("HangThucPham".equals(loaiHang)) {
            ngaySanXuatField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            ngayHetHanField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
            nhaCungCapField.setText((String) tableModel.getValueAt(selectedRow, 6));
        } else if ("HangDienMay".equals(loaiHang)) {
            thoiGianBaoHanhField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            congSuatField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
        } else if ("HangSanhSu".equals(loaiHang)) {
            nhaSanXuatField.setText((String) tableModel.getValueAt(selectedRow, 4));
            ngayNhapKhoField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
        }
    }

    private void updateFormVisibility(String loaiHang) {
        // Hide food fields and Labels
        ngaySanXuatField.setVisible(false);
        ngaySanXuatLabel.setVisible(false);
        ngayHetHanField.setVisible(false);
        ngayHetHanLabel.setVisible(false);
        nhaCungCapField.setVisible(false);
        nhaCungCapLabel.setVisible(false);

        // Hide electronics fields and Labels
        thoiGianBaoHanhField.setVisible(false);
        thoiGianBaoHanhLabel.setVisible(false);
        congSuatField.setVisible(false);
        congSuatLabel.setVisible(false);

        // Hide ceramics fields and Labels
        nhaSanXuatField.setVisible(false);
        nhaSanXuatLabel.setVisible(false);
        ngayNhapKhoField.setVisible(false);
        ngayNhapKhoLabel.setVisible(false);

        // Show relevant text fields based on selected loaiHang
        if ("HangThucPham".equals(loaiHang)) {
            ngaySanXuatField.setVisible(true);
            ngaySanXuatLabel.setVisible(true);
            ngayHetHanField.setVisible(true);
            ngayHetHanLabel.setVisible(true);
            nhaCungCapField.setVisible(true);
            nhaCungCapLabel.setVisible(true);
        } else if ("HangDienMay".equals(loaiHang)) {
            thoiGianBaoHanhField.setVisible(true);
            thoiGianBaoHanhLabel.setVisible(true);
            congSuatField.setVisible(true);
            congSuatLabel.setVisible(true);
        } else if ("HangSanhSu".equals(loaiHang)) {
            nhaSanXuatField.setVisible(true);
            nhaSanXuatLabel.setVisible(true);
            ngayNhapKhoField.setVisible(true);
            ngayNhapKhoLabel.setVisible(true);
        }
    }

    @Override
    public void update() {
        String loaiHang = (String) loaiHangComboBox.getSelectedItem();
        loadTableData(loaiHang);
    }

    public JTextField getMaHangField() {
        return maHangField;
    }

    public JTextField getTenHangField() {
        return tenHangField;
    }

    public void setTenHangField(JTextField tenHangField) {
        this.tenHangField = tenHangField;
    }

    public JTextField getSoLuongTonField() {
        return soLuongTonField;
    }

    public JTextField getDonGiaField() {
        return donGiaField;
    }

    public JTextField getNgaySanXuatField() {
        return ngaySanXuatField;
    }

    public JTextField getNgayHetHanField() {
        return ngayHetHanField;
    }

    public JTextField getNhaCungCapField() {
        return nhaCungCapField;
    }

    public JTextField getThoiGianBaoHanhField() {
        return thoiGianBaoHanhField;
    }

    public JTextField getCongSuatField() {
        return congSuatField;
    }

    public JTextField getNhaSanXuatField() {
        return nhaSanXuatField;
    }

    public void setNhaSanXuatField(JTextField nhaSanXuatField) {
        this.nhaSanXuatField = nhaSanXuatField;
    }

    public JTextField getNgayNhapKhoField() {
        return ngayNhapKhoField;
    }

    public JComboBox<String> getLoaiHangComboBox() {
        return loaiHangComboBox;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public JTable getTable() {
        return table;
    }
}
