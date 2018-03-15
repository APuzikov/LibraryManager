package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.Book;
import ru.mera.lib.entity.RecordCard;
import ru.mera.lib.repository.RecordCardRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordCardService {

    @Autowired
    private RecordCardRepository recordCardRepository;

    @Autowired
    private PupilService pupilService;

    @Autowired
    private BookService bookService;

    public JsonResponse giveBook(int bookId, int pupilId){
        Book book = bookService.getOneBook(bookId);
        if (book != null ){
            if(book.getCount() > 0) {
                if (pupilService.getOnePupil(pupilId) != null) {
                    if (recordCardRepository.findByBookIdAndPupilIdAndReturnDate(bookId, pupilId, null) == null) {
                        RecordCard recordCard = new RecordCard();
                        SimpleDateFormat dateFormat = new SimpleDateFormat();

                        recordCard.setBookId(bookId);
                        recordCard.setPupilId(pupilId);
                        recordCard.setReceiveDate(dateFormat.format(new Date()));
                        recordCardRepository.save(recordCard);

                        book.setCount(book.getCount() - 1);
                        bookService.saveBook(book);
                        return new JsonResponse(true, "The book was successfully received by pupil!");
                    } else return new JsonResponse(false,"This book has already been given to the pupil!");
                } else return new JsonResponse(false, "Pupil isn't exist");
            } else return new JsonResponse(false, "Book is not in the library!");
        } else return new JsonResponse(false, "Book isn't exist!");
    }

    public JsonResponse returnBook(int bookId, int pupilId){
        RecordCard recordCard = recordCardRepository.findByBookIdAndPupilIdAndReturnDate(bookId, pupilId, null);
        if (recordCard != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            recordCard.setReturnDate(dateFormat.format(new Date()));
            recordCardRepository.save(recordCard);

            Book book = bookService.getOneBook(bookId);
            book.setCount(book.getCount() + 1);
            bookService.saveBook(book);
            return new JsonResponse(true, "Book was successfully returned!");
        } else return new JsonResponse(false, "This book was not given to this pupil!");
    }
}
