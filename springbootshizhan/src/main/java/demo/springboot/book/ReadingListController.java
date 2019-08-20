package demo.springboot.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static java.lang.System.out;

/**
 * @author: wq
 * @date: 2018/7/17 15:29
 */
@Controller
@RequestMapping("/readinglist")
public class ReadingListController {

    @Autowired
    private ReadingListRepository readingListRepository;

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model){

        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null){
            model.addAttribute("books", readingList);
        }
        return "readinglist";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readinglist/{reader}";
    }
}
