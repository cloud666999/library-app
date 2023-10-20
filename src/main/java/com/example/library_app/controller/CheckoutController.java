package com.example.library_app.controller;


import com.example.library_app.model.Account;
import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout;
import com.example.library_app.model.dto.CheckOutDt;
import com.example.library_app.service.AccountService;
import com.example.library_app.service.CheckoutService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/checkout")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckoutController {
    CheckoutService checkoutService;

    AccountService accountService;

    Checkout_DetailsMapper checkoutDetailsMapper;

    CheckoutMapper mapper;

    @GetMapping("/findall")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<CheckoutResp> findALl(@ModelAttribute CheckoutFilter checkoutFilter, Pageable pageable) {
        return  checkoutService.findALl(checkoutFilter, pageable).map(mapper::toCheckoutResp);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CheckoutResp findById(@PathVariable Long id)  {
        return checkoutService.findById(id).transform(mapper::toCheckoutResp);
    }

    @PostMapping("/{id}/createCheckout")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public CheckoutResp createCheckOut(@PathVariable Long id,@RequestBody CheckoutReq checkoutReq, Principal principal) {
        log.info("create checkout: {}, username: {}", checkoutReq, principal.getName());

//        return  checkoutReq.transform(mapper::toCheckout)
//                .transform(checkoutService::createCheckOut)
//                .transform(mapper::toCheckoutResp);

        return checkoutService.createCheckOut(id, mapper.toCheckout(checkoutReq))
                .transform(mapper::toCheckoutResp);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkoutService.deleteById(id);
    }


    @PostMapping("{id}/checkoutdetails")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public  CheckoutResp addCheckoutDetails(@PathVariable Long id, @RequestBody Checkout_DetailsReq detailsCheckoutReq) {
        return checkoutService.addCheckOutDetails(id, checkoutDetailsMapper.toCheckoutDetails(detailsCheckoutReq))
                .transform(mapper::toCheckoutResp);
    }

}
