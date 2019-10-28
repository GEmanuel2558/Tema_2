package sda_tema_2_spring.Tema_2.business.service;

import com.google.common.hash.Hashing;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sda_tema_2_spring.Tema_2.data.entity.PermissionEntity;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;
import sda_tema_2_spring.Tema_2.data.repository.UserDao;
import sda_tema_2_spring.Tema_2.data.repository.UserPermissionDao;
import sda_tema_2_spring.Tema_2.utils.EncryptionHelper;
import sda_tema_2_spring.Tema_2.web.dto.TokenRequestDto;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@Scope(value = "singleton")
public class CredentialsService implements ICredentialsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPermissionDao upDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserByToken(@NotNull String token) {
        return userDao.findUserEntityByPassword(token);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionEntity> returnAllPermissionForUser(@NotNull Integer userId) {
        return upDao.returnAllPermissionForUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserEntitiesByEmail(@NotNull @Length(min = 1) String userName) {
        return userDao.findUserEntitiesByEmail(userName);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public UserEntity signup(@NotNull TokenRequestDto authenticationRequest) {
        EncryptionHelper encryptionHelper=EncryptionHelper.getInstance();
        UserEntity newUser = new UserEntity(authenticationRequest.getUsername(),
                encryptionHelper.plainTextToHash(authenticationRequest.getPassword()), authenticationRequest.getEmail());
        return userDao.save(newUser);
    }
}
