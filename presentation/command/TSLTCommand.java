package presentation.command;

import presentation.HangHoaUI;

public class TSLTCommand implements Command {
    private HangHoaUI hangHoaUI;

    public TSLTCommand(HangHoaUI hangHoaUI) {
        this.hangHoaUI = hangHoaUI;
    }

    public void execute() {
        hangHoaUI.tongSoLuongTon();
    }
    
}
