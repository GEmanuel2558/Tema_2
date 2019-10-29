package sda_tema_2_spring.Tema_2.web.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;
import sda_tema_2_spring.Tema_2.data.repository.PermissionDao;
import sda_tema_2_spring.Tema_2.data.repository.UserDao;
import sda_tema_2_spring.Tema_2.exceptions.custom.NoInformationInTheDbException;
import sda_tema_2_spring.Tema_2.web.dto.PermissionDto;
import sda_tema_2_spring.Tema_2.web.dto.UserDto;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private PermissionDao permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") @Min(value = 0)  Integer userId) {
        Optional<UserEntity> searchedUSer = userRepository.findById(userId);
        if (searchedUSer.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(searchedUSer.get(), UserDto.class));
        } else {
            throw new NoInformationInTheDbException();
        }
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        return ResponseEntity.ok(StreamSupport
                .stream(permissionRepository.findAll().spliterator(), false)
                .map(permissionEntity -> modelMapper.map(permissionEntity, PermissionDto.class))
                .collect(Collectors.toList()));
    }


}
