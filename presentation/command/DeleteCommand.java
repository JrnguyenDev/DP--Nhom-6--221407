package presentation.command;

import domain.model.HangHoa;
import presentation.HangHoaUI;

public class DeleteCommand implements Command {
    private HangHoaUI hangHoaUI;
    private HangHoa hangHoa;
    public DeleteCommand(HangHoaUI hangHoaUI) {
        this.hangHoaUI = hangHoaUI;
    }

    public void execute() {
        hangHoaUI.deleteHangHoa(hangHoa);
    }
    
}
