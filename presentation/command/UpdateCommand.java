package presentation.command;

import presentation.HangHoaUI;

public class UpdateCommand implements Command {
    private HangHoaUI hangHoaUI;

    public UpdateCommand(HangHoaUI hangHoaUI) {
        this.hangHoaUI = hangHoaUI;
    }

    public void execute() {
        hangHoaUI.updateHangHoa();
    }
    
}
