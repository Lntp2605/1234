import com.example.hospitalmanagement.model.Examination;
import com.example.hospitalmanagement.service.ExaminationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/examinations")
public class ExaminationController {

    private final ExaminationService examinationService;

    public ExaminationController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/list")
    public String listExaminations(Model model) {
        List<Examination> examinations = examinationService.getAllExaminations();
        model.addAttribute("examinations", examinations);
        return "examinations/list"; // view HTML hiển thị danh sách
    }
}
