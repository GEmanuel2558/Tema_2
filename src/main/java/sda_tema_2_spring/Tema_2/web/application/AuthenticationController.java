package sda_tema_2_spring.Tema_2.web.application;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sda_tema_2_spring.Tema_2.business.service.ICredentialsService;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;
import sda_tema_2_spring.Tema_2.utils.EncryptionHelper;
import sda_tema_2_spring.Tema_2.web.dto.TokenRequestDto;
import sda_tema_2_spring.Tema_2.web.dto.TokenRequestWithOutUserNameDto;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping(value = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ICredentialsService credentials;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequestWithOutUserNameDto authenticationRequest) throws Exception {

        String requestEmail = authenticationRequest.getEmail();
        if (null != requestEmail && 1 < requestEmail.trim().length()) {
            EncryptionHelper encryptionHelper=EncryptionHelper.getInstance();
            String passwordHash = encryptionHelper.plainTextToHash(authenticationRequest.getPassword());
            final Optional<UserEntity> userDetails = credentials.findUserEntitiesByEmail(requestEmail);
            if (userDetails.isPresent()) {
                if (passwordHash.equals(userDetails.get().getPassword())) {
                    //authenticate(requestEmail, authenticationRequest.getPassword());
                    return ResponseEntity.ok(passwordHash);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserEntity> signup(@RequestBody TokenRequestDto authenticationRequest) {
        UserEntity newCreated = credentials.signup(authenticationRequest);
        return ResponseEntity.created(URI.create("http://localhost:8098/user/" + newCreated.getId())).build();
    }

}
