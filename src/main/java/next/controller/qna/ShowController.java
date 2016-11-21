package next.controller.qna;

import core.mvc.ModelAndView;
import next.controller.user.AbstractController;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Kj Nam
 * @since 2016-11-19
 */
public class ShowController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao dao = new QuestionDao();
        Question quesiton = dao.findByQuestionId(questionId);
        req.setAttribute("question", quesiton);

        AnswerDao answerDao = new AnswerDao();
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        req.setAttribute("answers", answers);

        return jspView("/qna/show.jsp");
    }
}