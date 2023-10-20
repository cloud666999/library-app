package com.example.library_app.service;

import com.example.library_app.controller.BookFilter;
import com.example.library_app.controller.CheckoutFilter;
import com.example.library_app.model.Account;
import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout;
import com.example.library_app.model.Checkout_Details;
import com.example.library_app.model.exception.AccountNotFoundException;
import com.example.library_app.model.exception.BookNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.BookRepository;
import com.example.library_app.repository.CheckoutRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DomainCheckoutServiceImp  implements CheckoutService{
    CheckoutRepository checkoutRepository;

    BookRepository bookRepository;

    AccountService accountService;


    @Override
    public Page<Checkout> findALl(CheckoutFilter bookFilter, Pageable pageable) {
        return checkoutRepository.findALl(bookFilter, pageable);
    }

    @Override
    public Checkout findById(Long id) {
        return checkoutRepository.findById(id).orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND," this checkout is not available with id" + id));
    }


    @Override
    public Checkout save(Checkout checkout) {
        return checkoutRepository.save(checkout);
    }

    @Override
    @Transactional
    public Checkout createCheckOut(Long userId,Checkout checkout) {
        Account account = accountService.findById(userId);
        if(account == null) {
            throw  new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "This user is not available");

        }
        checkout.setAccount(account);

        if(CollectionUtils.isNotEmpty(checkout.getCheckoutDetails())) {
                checkout.getCheckoutDetails().forEach(checkoutDetails -> checkoutDetails.setCheckout(checkout));
        }
        return checkoutRepository.save(checkout);
    }


    @Override
    public void deleteById(Long id) {
        checkoutRepository.deleteById(id);
    }

//    @Override
//    public Checkout addUserId(Long id, Account account) {
//       var checkout = findById(id);
//        checkout.setAccount(account);
//
//        return checkoutRepository.save(checkout);
//    }

        @Override
        public Checkout addCheckOutDetails(Long id, Checkout_Details checkoutDetails) {
            var checkout = findById(id);
            // Thay vì tạo mới checkoutDetails, hãy kiểm tra xem nó đã tồn tại trong cơ sở dữ liệu hay chưa.
            // Nếu đã tồn tại, sử dụng nó; nếu chưa, thêm vào.
            // Đảm bảo sử dụng book có sẵn trong cơ sở dữ liệu, không tạo mới book.

            // Tìm book trong cơ sở dữ liệu bằng ID và gán cho checkoutDetails.
            Book book = bookRepository.findById(checkoutDetails.getBook().getId()).orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND, " Book was not found"));
            checkoutDetails.setCheckout(checkout);
            checkoutDetails.setBook(book);

            checkout.getCheckoutDetails().add(checkoutDetails);

            return  checkoutRepository.save(checkout);
        }
    }
