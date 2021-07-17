package api.doc.docman.Service;

import api.doc.docman.domain.User;
import api.doc.docman.domain.UserAuthority;
import api.doc.docman.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getById(username);
    }

    public String save(User user) {
        user.setEnabled(true);
        User newUser = userRepository.save(user);
        addAuthority(newUser.getUsername(), "USER");
        return "SUCCESS";
    }

    public void addAuthority(String userId, String authority){

        userRepository.findById(userId).ifPresent(user->{
            UserAuthority newRole = new UserAuthority(user.getUsername(), authority);
            if(user.getAuthorities() == null){
                HashSet<UserAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }else if(!user.getAuthorities().contains(newRole)){
                HashSet<UserAuthority> authorities = new HashSet<>(user.getAuthorities());

                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

    public void removeAuthority(String userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            if(user.getAuthorities()==null) return;
            UserAuthority targetRole = new UserAuthority(user.getUsername(), authority);
            if(user.getAuthorities().contains(targetRole)){
                user.setAuthorities(
                        user.getAuthorities().stream().filter(auth->!auth.equals(targetRole))
                                .collect(Collectors.toSet())
                );
                save(user);
            }
        });
    }
}
