package presentation.command;

import presentation.HangHoaUI;

public class AddCommand implements Command {
    private HangHoaUI hangHoaUI;

    public AddCommand(HangHoaUI hangHoaUI) {
        this.hangHoaUI = hangHoaUI;
    }

    public void execute() {
        hangHoaUI.addHangHoa();
    }
    
}
