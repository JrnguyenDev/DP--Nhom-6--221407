package presentation;

import domain.model.HangHoa;
import domain.observer.Publisher;
import domain.observer.Subcriber;
import domain.service.HangHoaService;
import domain.service.HangHoaServiceImpl;
import persistence.HangHoaDAO;
import persistence.HangHoaDAOImpl;
import presentation.command.AddCommand;
import presentation.command.Command;
import presentation.command.DeleteCommand;
import presentation.command.TSLTCommand;
import presentation.command.UpdateCommand;
public class HangHoaApp {
    public static void main(String[] args) {
        HangHoaDAO hangHoaDAO = new HangHoaDAOImpl();
        HangHoaService hangHoaService = new HangHoaServiceImpl(hangHoaDAO);
        HangHoa hangHoa = new HangHoa();
        HangHoaUI hangHoaUI = new HangHoaUI(hangHoaService,hangHoa);       
        Publisher publisher = new Publisher();
        publisher.subcribe((Subcriber) hangHoaUI);
        Command addCommand = new AddCommand(hangHoaUI);
        Command tsltCommand = new TSLTCommand(hangHoaUI);
        Command deleteCommand = new DeleteCommand(hangHoaUI);
        Command updateCommand = new UpdateCommand(hangHoaUI);
        hangHoaUI.setVisible(true);
    }
}
