package com.example.library_app.controller;


import com.example.library_app.controller.validation.AdminAccountReq;
import com.example.library_app.model.exception.AccountNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class AccountController {
    AccountService accountService;

    AccountMapper accountMapper;

    PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<AccountResp> findAll(@ModelAttribute AccountFilter filter, Pageable pageable) {
            return accountService.findAll(filter,pageable).map(accountMapper::toAccountResp);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public  AccountResp findById(@PathVariable Long id) {
        return  accountService.findById(id).transform(accountMapper::toAccountResp);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    //Admin mở tài khoản hộ user
    public  AccountResp createAccount(@RequestBody  AdminAccountReq adminAccountReq) {
        var account = accountMapper.toAccount(adminAccountReq);

        var rawPwd = generatePwd();
        account.setPassword(passwordEncoder.encode(rawPwd));
        account.setRole(adminAccountReq.role());

        var res = accountMapper.toAccountResp(accountService.save(account));
        notifyPassword(account.getUsername(), rawPwd);

        return  res;
    }

    private void notifyPassword(String username, String rawPwd) {
        log.info("Send password to {} with password {}", username, rawPwd);
    }

    // tự động gen ra một chuỗi ký tự bất kỳ
    private String generatePwd() {
        return RandomStringUtils.randomAlphanumeric(8);
    }


    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public AccountResp update(@PathVariable("id") Long id,@RequestBody @Valid AccountUpdateReq req) {
        var acc = accountService.findById(id);
        accountMapper.update(req, acc);
        return accountMapper.toAccountResp(accountService.save(acc));
    }


    // thay vì xóa tài khoản sẽ disable tài khoản đó đi
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public void delete(@PathVariable("id") Long id) {
        accountService.findById(id).transform(a -> {
            a.setEnable(false);
            return accountService.save(a);
        });
    }

    @GetMapping ("/reactive/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public void reActiveAccount(@PathVariable("username") String username) {
//        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        accountService.findByUsername(username).orElseThrow(() -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND,"this username is not available "))
                .transform(a -> {
                    a.setEnable(true);
                    return accountService.save(a);
                });
    }

}
