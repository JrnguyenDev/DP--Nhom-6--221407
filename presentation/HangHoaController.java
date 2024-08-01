package presentation;

import javax.swing.JOptionPane;

import domain.model.HangHoa;
import presentation.command.AddCommand;
import presentation.command.Command;
import presentation.command.DeleteCommand;
import presentation.command.TSLTCommand;
import presentation.command.UpdateCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangHoaController implements ActionListener{
    private HangHoaUI hangHoaUI;

    HangHoaController(HangHoaUI hangHoaUI, HangHoa hangHoa) {
        this.hangHoaUI = hangHoaUI;
    }
    
    public void executeCommand(Command command) {
        command.execute();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Thêm hàng hóa":
                executeCommand(new AddCommand(hangHoaUI));
                JOptionPane.showMessageDialog(hangHoaUI, "Hàng hóa đã được thêm");
                break;
            case "Xóa hàng hóa":
                executeCommand(new DeleteCommand(hangHoaUI));
                JOptionPane.showMessageDialog(hangHoaUI, "Xóa hàng hóa thành công!");
                break;
            case "Sửa hàng hóa":
                executeCommand(new UpdateCommand(hangHoaUI));
                JOptionPane.showMessageDialog(hangHoaUI, "Sửa hàng hóa thành công");
                break;
            case "Tổng số lượng tồn":
                executeCommand(new TSLTCommand(hangHoaUI));
                break;
            default:
                break;
        }
    }
}
