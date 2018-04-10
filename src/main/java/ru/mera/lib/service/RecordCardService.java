package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public ResponseEntity giveBook(int bookId, int pupilId){
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
                        bookService.updateBook(book);
                        return new ResponseEntity(HttpStatus.OK);
                    } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }  else return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }  else return new ResponseEntity(HttpStatus.BAD_REQUEST);
//                        return new OperationStatus(true, "The book was successfully received by pupil!");
//                    } else return new OperationStatus(false,"This book has already been given to the pupil!");
//                } else return new OperationStatus(false, "Pupil isn't exist");
//            } else return new OperationStatus(false, "Book is not in the library!");
//        } else return new OperationStatus(false, "Book isn't exist!");
    }

    public ResponseEntity returnBook(int bookId, int pupilId){
        RecordCard recordCard = recordCardRepository.findByBookIdAndPupilIdAndReturnDate(bookId, pupilId, null);
        if (recordCard != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            recordCard.setReturnDate(dateFormat.format(new Date()));
            recordCardRepository.save(recordCard);

            Book book = bookService.getOneBook(bookId);
            book.setCount(book.getCount() + 1);
            bookService.updateBook(book);
            return new ResponseEntity(HttpStatus.OK);
        }  else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
