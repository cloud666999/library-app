package com.example.library_app.controller.client;



import com.example.library_app.controller.AccountMapper;
import com.example.library_app.controller.AccountReq;
import com.example.library_app.controller.AccountResp;
import com.example.library_app.controller.AccountUpdateReq;
import com.example.library_app.model.Role;
import com.example.library_app.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/client/accounts")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class ClientAccountController {
    AccountService service;

    AccountMapper mapper;

    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AccountResp register(@RequestBody @Valid AccountReq req) {
        var acc = mapper.toAccount(req);
        acc.setPassword(passwordEncoder.encode(req.password()));
        acc.setRole(Role.USER);
        return mapper.toAccountResp(service.save(acc));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('USER')")
    public AccountResp getMe(Principal principal) {
        // Principal là đối tượng đang thực hiện hành động này
        return service.getByUsername(principal.getName()).transform(mapper::toAccountResp);
    }


    @PatchMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public  AccountResp update(Principal principal,@RequestBody AccountUpdateReq req) {
        var acc = service.getByUsername(principal.getName());
        mapper.update(req,acc);
        return mapper.toAccountResp(service.save(acc));
    }


    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Principal principal) {
            service.getByUsername(principal.getName())
                    .transform(a -> {
                        a.setEnable(false);
                        return service.save(a);
                    });
    }
}
